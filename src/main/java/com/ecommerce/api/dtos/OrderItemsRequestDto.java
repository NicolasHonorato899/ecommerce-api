package com.ecommerce.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record OrderItemsRequestDto(
    @NotBlank
    String productId,
    @NotBlank
    String quantity,
    @NotBlank
    String orderId
) {
}
