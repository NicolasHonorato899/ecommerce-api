package com.ecommerce.api.orders;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import com.ecommerce.api.orders.OrdersModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(Authentication authentication) {
        var order = ordersService.checkout(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    @GetMapping
    public ResponseEntity<?> getOrders(Authentication authentication){
        var orders = ordersService.getOrdersByUser(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId, Authentication authentication){
        var order = ordersService.getOrderById(orderId, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
