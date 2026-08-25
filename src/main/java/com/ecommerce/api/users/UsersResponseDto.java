package com.ecommerce.api.users;

public record UsersResponseDto(
    String email,
    String name,
    String address
) {}
