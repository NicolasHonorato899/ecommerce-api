package com.ecommerce.api.dtos;

import java.time.LocalDateTime;

public record OrdersResponseDto(

    String id,
    String user_email,
    String status,
    Double amount,
    LocalDateTime created_at

) {}
