package com.ecommerce.api.orders;

import java.time.LocalDateTime;

public record OrdersResponseDto(

    String id,
    String user_email,
    String status,
    Double amount,
    LocalDateTime created_at

) {}
