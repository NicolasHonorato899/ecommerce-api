package com.ecommerce.api.payments;

import java.sql.Timestamp;

public record PaymentsResponseDto(
        String status,
        String paymentMethod,
        String paymentId,
        String orderId,
        Float amount,
        String currency,
        String gateway,
        Timestamp createdAt,
        Timestamp updatedAt
) {
}
