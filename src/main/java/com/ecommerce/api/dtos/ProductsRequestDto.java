package com.ecommerce.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductsRequestDto(
        @NotNull
        String productId,

        @NotBlank
        String name,

        @NotBlank
        String description,

        @NotNull
        Double price
) {}
