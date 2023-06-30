package com.apibrief3.record.categoryRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddCategoryRequest(
        @NotEmpty(message = "name cannot be empty")
        @NotNull(message = "name cannot be null")
        String name


) {
}
