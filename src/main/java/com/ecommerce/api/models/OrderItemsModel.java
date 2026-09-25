package com.ecommerce.api.models;

import com.ecommerce.api.models.OrdersModel;
import com.ecommerce.api.models.ProductsModel;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@Table(name = "order_items")
public class OrderItemsModel implements Persistable<String>{
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrdersModel order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductsModel product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_price")
    private double unitPrice;

    @Transient
    private boolean isNewItem = true;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNewItem;
    }

    @PostLoad
    @PostPersist
    public void markAsNotNew() {
        this.isNewItem = false;
    }
}
