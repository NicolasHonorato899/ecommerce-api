package com.ecommerce.api.cart_items;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CartItemsRequestDto(
    @NotBlank
    String productId,
    @Positive
    int quantity
)
{}
