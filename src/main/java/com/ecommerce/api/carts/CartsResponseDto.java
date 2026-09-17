package com.ecommerce.api.carts;

import java.time.LocalDateTime;

public record CartsResponseDto(
        String cartId,
        String userEmail,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
