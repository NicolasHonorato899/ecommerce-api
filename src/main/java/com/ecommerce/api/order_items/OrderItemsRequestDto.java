package com.ecommerce.api.order_items;

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
