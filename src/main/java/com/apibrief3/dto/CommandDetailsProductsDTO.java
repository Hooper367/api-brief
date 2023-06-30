package com.apibrief3.dto;

public record CommandDetailsProductsDTO(
        Integer id,

        ProductDTO product,

        Float price
) {
}
