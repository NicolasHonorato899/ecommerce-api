package com.ecommerce.api.payments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentsController {
    @Autowired
    private IPaymentsRepository paymentsRepository;

    @GetMapping("/{orderId}")
    public ResponseEntity<?> create(@PathVariable String orderId){

    }
}
