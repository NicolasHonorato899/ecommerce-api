package com.ecommerce.api.dtos;

public record CartItemsResponseDto(
        String id,
        String productId,
        String productName,
        int quantity,
        double unitPrice,
        double subtotal
) {}
