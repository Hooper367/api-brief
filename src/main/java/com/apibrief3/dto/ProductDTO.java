package com.apibrief3.dto;

public record ProductDTO(
        Integer id,

        String name,

        String description,

        ColorDTO color,

        SizeDTO size,

        Float price,

        PromotionDTO promotion,

        CategoryDTO category
) {
}
