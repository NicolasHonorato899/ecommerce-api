package com.ecommerce.api.cart_items;

import com.ecommerce.api.carts.CartsModel;
import com.ecommerce.api.products.ProductsModel;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@Table(name = "cart_items")
public class CartItemsModel implements Persistable<String> {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private CartsModel cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductsModel product;

    @Column(name = "quantity")
    private int quantity;

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
    void markAsNotNew(){
        this.isNewItem = false;
    }
}
