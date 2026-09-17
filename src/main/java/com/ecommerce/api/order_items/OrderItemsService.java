package com.ecommerce.api.order_items;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.api.order_items.OrderItemsModel;
import com.ecommerce.api.orders.OrdersModel;
import com.ecommerce.api.cart_items.CartItemsModel;
import com.ecommerce.api.order_items.IOrderItemsRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;


@Service
@Transactional
public class OrderItemsService {

    @Autowired
    private IOrderItemsRepository orderItemsRepository;

    public OrderItemsModel freezeFromCartItem(OrdersModel order, CartItemsModel cartItem) {
        var orderItem = new OrderItemsModel();
        orderItem.setId(UUID.randomUUID().toString());
        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setUnitPrice(cartItem.getProduct().getPrice());
        return orderItemsRepository.save(orderItem);

    }
}
