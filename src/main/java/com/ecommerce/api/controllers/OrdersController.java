package com.ecommerce.api.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.ecommerce.api.services.OrdersService;
import com.ecommerce.api.repositories.IUsersRepository;
import com.ecommerce.api.dtos.OrdersResponseDto;


@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private IUsersRepository usersRepository;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(Authentication authentication) {
        var order = ordersService.checkout(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    @GetMapping
    public ResponseEntity<?> getOrders(Authentication authentication, @RequestParam(required = false) Boolean isAdmin){
        boolean shouldFetchAll = false;

        if (isAdmin != null && isAdmin) {
            var user = usersRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

            if (!"ADMIN".equals(user.getRole())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access all orders");
            }
            shouldFetchAll = true;
        }

        var orders = ordersService.findAll(authentication.getName(), shouldFetchAll);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId, Authentication authentication){
        var order = ordersService.getOrderById(orderId, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
