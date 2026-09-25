package com.ecommerce.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersRedirectController {

    @GetMapping("/{orderId}/success")
    public ResponseEntity<?> success(@PathVariable String orderId) {
        return ResponseEntity.ok("Payment completed for order " + orderId + ". You may close this window.");
    }

    @GetMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancel(@PathVariable String orderId) {
        return ResponseEntity.ok("Payment cancelled for order " + orderId + ".");
    }
}
