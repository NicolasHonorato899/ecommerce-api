package com.ecommerce.api.cart_items;

public record CartItemsResponseDto(
        String id,
        String productId,
        String productName,
        int quantity,
        double unitPrice,
        double subtotal
) {}
