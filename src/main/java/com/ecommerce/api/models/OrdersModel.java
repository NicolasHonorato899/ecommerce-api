package com.ecommerce.api.models;

import com.ecommerce.api.models.UsersModel;
import com.ecommerce.api.models.OrderItemsModel;
import com.ecommerce.api.models.PaymentsModel;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.Persistable;
import java.util.List;

@Data
@Entity(name = "orders")
public class OrdersModel implements Persistable<String> {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private UsersModel user;

    @Column(name = "status")
    private String status;

    @Column(name = "amount")
    private Double amount;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItemsModel> orderItems;

    @OneToOne(mappedBy = "order")
    private PaymentsModel payment;

    @Transient
    private boolean isNewOrder = true;

    @Override
    public String getId() {
        return orderId;
    }

    @Override
    public boolean isNew() {
        return isNewOrder;
    }

    @PostLoad
    @PostPersist
    void markAsNotNew() {
        isNewOrder = false;
    }

}
