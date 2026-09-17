package com.ecommerce.api.products;

public record ProductResponseDto(
    String productId,
    String name,
    String description,
    double price
) {}
