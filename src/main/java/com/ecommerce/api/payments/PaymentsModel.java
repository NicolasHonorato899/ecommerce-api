package com.ecommerce.api.payments;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Data
@Entity(name = "payments")
public class PaymentsModel {
    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Column(unique = true)
    private String orderId;
    private float amount;
    private String currency;
    private String status;
    private String paymentMethod;
    private String gateway;
    private String gatewayTransactionId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
