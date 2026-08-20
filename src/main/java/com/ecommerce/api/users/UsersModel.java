package com.ecommerce.api.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "users")
public class UsersModel {
    @Id
    private String email;

    @Column(unique = true)
    private String password;
    private String address;

}
