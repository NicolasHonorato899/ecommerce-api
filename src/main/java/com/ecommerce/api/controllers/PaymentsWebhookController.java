package com.ecommerce.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.api.services.PaymentsService;


@RestController
public class PaymentsWebhookController {

    @Autowired
    private PaymentsService paymentsService;

    @Value("${stripe.webhook-secret}")
    private String stripeWebhookSecret;

    @PostMapping("/payments/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String payload,
                                           @RequestHeader("Stripe-Signature") String stripeSignature) {
        System.out.println(">>> Secret carregado pela aplicação: [" + stripeWebhookSecret + "]");
        Event event;
        try {
            event = Webhook.constructEvent(payload, stripeSignature, stripeWebhookSecret);
        } catch (SignatureVerificationException e) {
            System.out.println(">>> Falha na verificação de assinatura: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if("checkout.session.completed".equals(event.getType())){
            var session = (Session) event.getDataObjectDeserializer()
                    .getObject()
                    .orElseThrow(() -> new IllegalArgumentException("Could not deserialize session"));
            paymentsService.markAsPaid(session.getId());
        }

        return ResponseEntity.ok("Webhook handled successfully");
    }
}
