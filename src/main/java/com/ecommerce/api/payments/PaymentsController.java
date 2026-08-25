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

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody PaymentsModel paymentsModel) {
        if (paymentsRepository.existsById(paymentsModel.getPaymentId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment already exists");
        }
        var savedPayment = paymentsRepository.save(paymentsModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
    }
}
