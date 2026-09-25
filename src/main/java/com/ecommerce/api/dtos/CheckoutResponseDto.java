package com.ecommerce.api.dtos;

public record CheckoutResponseDto(
    String orderId,
    double amount,
    String status,
    String checkoutUrl
) {}
