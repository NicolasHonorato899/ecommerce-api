package com.ecommerce.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.ecommerce.api.services.PaymentsService;
import com.ecommerce.api.dtos.PaymentsResponseDto;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    @Autowired
    private PaymentsService paymentsService;

    @GetMapping
    public ResponseEntity<?> getAllPayments(Authentication authentication){
        var payments = paymentsService.findAll(authentication.getName(), true);
        return ResponseEntity.status(HttpStatus.OK).body(payments);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getPaymentByOrderId(@PathVariable String orderId, Authentication authentication){
        PaymentsResponseDto payment = paymentsService.findByOrderId(orderId, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(payment);
    }
}
