package com.ecommerce.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsersRequestDto(
    @NotBlank
    @Email
    String email,

    @NotBlank
    String password,

    String name,

    String address
) {}
