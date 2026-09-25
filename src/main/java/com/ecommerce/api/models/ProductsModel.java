package com.ecommerce.api.models;

import com.ecommerce.api.models.OrderItemsModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.List;

@Data
@Entity(name = "products")
public class ProductsModel {

    @Column(name = "product_id")
    @Id
    private String productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItemsModel> orderItems;
}
