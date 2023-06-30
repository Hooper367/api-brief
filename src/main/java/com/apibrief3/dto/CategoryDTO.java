package com.apibrief3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDTO(
        Integer id,

        String name,

        PromotionDTO promotion,

        List<ProductDTO> products
) {
}
