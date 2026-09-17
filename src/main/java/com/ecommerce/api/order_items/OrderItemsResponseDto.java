package com.ecommerce.api.order_items;

public record OrderItemsResponseDto(
    String id,
    String productId,
    String orderId,
    Integer quantity,
    Integer unit_price
) {}
