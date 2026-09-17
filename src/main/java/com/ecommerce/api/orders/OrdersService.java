package com.ecommerce.api.orders;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.ecommerce.api.carts.ICartsRepository;
import com.ecommerce.api.orders.IOrdersRepository;
import com.ecommerce.api.order_items.OrderItemsService;
import com.ecommerce.api.cart_items.ICartItemsRepository;
import com.ecommerce.api.users.IUsersRepository;
import com.ecommerce.api.users.UsersModel;

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

    private OrdersResponseDto toResponseDto(OrdersModel order){
        return new OrdersResponseDto(
            order.getOrderId(),
            order.getUser().getEmail(),
            order.getStatus(),
            order.getAmount(),
            order.getCreatedAt()
        );
    }

    public OrdersResponseDto checkout(String email) {
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

        for(var item : items){
            var product = item.getProduct();
            int quantity = item.getQuantity();

            if(product.getStock() < quantity){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product: " + product.getName() + " stock is not enough");
            }

            product.setStock(product.getStock() - quantity);

            var orderItem = orderItemsService.freezeFromCartItem(order, item);
            total += orderItem.getUnitPrice() * orderItem.getQuantity();
        }

        order.setAmount(total);
        var savedOrder = ordersRepository.save(order);

        cart.setStatus("ordered");
        cartsRepository.save(cart);

        return toResponseDto(savedOrder);
    }

    public List<OrdersResponseDto> getOrdersByUser(String userEmail){
        return ordersRepository.findByUser_Email(userEmail)
            .stream()
            .map(this::toResponseDto)
            .toList();
    }

    public OrdersResponseDto getOrderById(String orderId, String requesterEmail){
        var order = ordersRepository.findByOrderId(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if(!order.getUser().getEmail().equals(requesterEmail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this order.");
        }
        return toResponseDto(order);
    }
}
