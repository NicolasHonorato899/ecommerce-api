package com.ecommerce.api.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.ecommerce.api.repositories.ICartsRepository;
import com.ecommerce.api.repositories.IOrdersRepository;
import com.ecommerce.api.repositories.ICartItemsRepository;
import com.ecommerce.api.repositories.IUsersRepository;
import com.ecommerce.api.models.OrdersModel;
import com.ecommerce.api.models.UsersModel;
import com.ecommerce.api.models.OrderItemsModel;
import com.ecommerce.api.dtos.OrdersResponseDto;
import com.ecommerce.api.dtos.CheckoutResponseDto;
import com.ecommerce.api.services.OrderItemsService;
import com.ecommerce.api.services.StripeService;

@Service
@Transactional
public class OrdersService{

    @Autowired
    private IOrdersRepository ordersRepository;
    @Autowired
    private ICartsRepository cartsRepository;
    @Autowired
    private OrderItemsService orderItemsService;
    @Autowired
    private ICartItemsRepository cartItemRepository;
    @Autowired
    private IUsersRepository usersRepository;
    @Autowired
    private StripeService stripeService;
    @Autowired
    private PaymentsService paymentsService;

    private OrdersResponseDto toResponseDto(OrdersModel order){
        return new OrdersResponseDto(
            order.getOrderId(),
            order.getUser().getEmail(),
            order.getStatus(),
            order.getAmount(),
            order.getCreatedAt()
        );
    }

    public CheckoutResponseDto checkout(String email) {
        var user = usersRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        var cart = cartsRepository.findByUserAndStatus(user, "active")
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        var items = cartItemRepository.findByCart(cart);
        if(items.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty");
        }

        var order = new OrdersModel();
        order.setUser(user);
        order.setOrderId(UUID.randomUUID().toString());
        order.setStatus("pending");

        double total = 0;
        List<OrderItemsModel> frozenItems = new ArrayList<>();

        for(var item : items){
            var product = item.getProduct();
            int quantity = item.getQuantity();

            if(product.getStock() < quantity){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product: " + product.getName() + " stock is not enough");
            }

            product.setStock(product.getStock() - quantity);

            var orderItem = orderItemsService.freezeFromCartItem(order, item);
            frozenItems.add(orderItem);
            total += orderItem.getUnitPrice() * orderItem.getQuantity();
        }

        order.setAmount(total);
        var savedOrder = ordersRepository.save(order);

        cart.setStatus("ordered");
        cartsRepository.save(cart);

        var stripeSession = stripeService.createCheckoutSession(savedOrder, frozenItems);
        paymentsService.createPendingPayment(savedOrder, stripeSession.sessionId());

        return new CheckoutResponseDto(savedOrder.getOrderId(), savedOrder.getAmount(), savedOrder.getStatus(), stripeSession.sessionUrl());
    }

    public OrdersResponseDto getOrderById(String orderId, String requesterEmail){
        var order = ordersRepository.findByOrderId(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if(!order.getUser().getEmail().equals(requesterEmail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this order.");
        }
        return toResponseDto(order);
    }

    public List<OrdersResponseDto> findAll(String requesterEmail, boolean isAdmin){
        return ordersRepository.findAll()
            .stream()
            .filter(order -> isAdmin || order.getUser().getEmail().equals(requesterEmail))
            .map(this::toResponseDto)
            .toList();
    }
}
