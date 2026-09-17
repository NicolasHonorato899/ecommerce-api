package com.ecommerce.api.payments;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import org.springframework.data.domain.Persistable;

@Data
@Entity(name = "payments")
public class PaymentsModel implements Persistable<String>{
    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrdersModel order;

    @Column(name = "amount")
    private float amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "gateway")
    private String gateway;

    @Column(name = "gateway_transaction_id")
    private String gatewayTransactionId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Transient
    private boolean isNewPayment = true;

    @Override
    public String getId() {
        return paymentId;
    }

    @Override
    public boolean isNew() {
        return isNewPayment;
    }

    @PostLoad
    @PostPersist
    private void markAsNotNew() {
        isNewPayment = false;
    }
}
