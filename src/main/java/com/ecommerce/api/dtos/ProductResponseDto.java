package com.ecommerce.api.dtos;

public record ProductResponseDto(
    String productId,
    String name,
    String description,
    double price
) {}
