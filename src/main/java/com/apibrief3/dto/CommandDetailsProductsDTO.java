package com.apibrief3.dto;

public record CommandDetailsProductsDTO(
        Integer id,

        ProductDTO product,

        Integer quantity,

        Float unitPrice,

        Float totalPrice
) {
}
