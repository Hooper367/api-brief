package com.apibrief3.dto;

import java.util.List;

public record CategoryDTO(
        Integer id,

        String name,
        List<ProductDTO> products
) {
}
