package com.apibrief3.dto;

public record StockDTO(
        Integer id,

        Integer quantity,

        ProductDTO product
) {
}
