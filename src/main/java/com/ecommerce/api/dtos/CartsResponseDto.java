package com.ecommerce.api.dtos;

import java.time.LocalDateTime;

public record CartsResponseDto(
        String cartId,
        String userEmail,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
