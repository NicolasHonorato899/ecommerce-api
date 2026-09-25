package com.ecommerce.api.models;

import com.ecommerce.api.models.UsersModel;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import org.springframework.data.domain.Persistable;

@Data
@Entity(name = "carts")
public class CartsModel implements Persistable<String>{
    @Id
    @Column(name = "cart_id")
    private String cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private UsersModel user;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    private boolean isNewCart = true;

    @Override
    public String getId(){
        return cartId;
    }

    @Override
    public boolean isNew(){
        return isNewCart;
    }

    @PostLoad
    @PostPersist
    void markAsNotNew(){
        this.isNewCart = false;
    }
}
