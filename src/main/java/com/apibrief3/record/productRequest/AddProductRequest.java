package com.apibrief3.record.productRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AddProductRequest(
        @NotEmpty(message = "name cannot be empty")
        @NotNull(message = "name cannot be null")
        String name,

        @NotEmpty(message = "description cannot be empty")
        @NotNull(message = "description cannot be null")
        String description,

        Integer colorId,

        Integer sizeId,

        @NotNull(message = "price cannot be null")
        Float price,

        Integer quantity,

        Integer categoryId
) {
}
