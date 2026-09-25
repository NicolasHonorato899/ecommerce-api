package com.ecommerce.api.dtos;

public record OrderItemsResponseDto(
    String id,
    String productId,
    String orderId,
    Integer quantity,
    Integer unit_price
) {}
