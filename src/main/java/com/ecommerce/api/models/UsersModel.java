package com.ecommerce.api.models;

import com.ecommerce.api.models.CartsModel;
import com.ecommerce.api.models.OrdersModel;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity(name = "users")
public class UsersModel {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<CartsModel> carts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrdersModel> orders;

    @Column(name = "role")
    private String role;
}
