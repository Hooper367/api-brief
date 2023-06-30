package com.apibrief3.record.categoryRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateCategoryRequest(
        @NotEmpty(message = "name cannot be empty")
        @NotNull(message = "name cannot be null")
        String name
) {
}
