package com.ecommerce.api.dtos;

public record StripeCheckoutResultDto(
        String sessionId,
        String sessionUrl
){}
