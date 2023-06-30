package com.apibrief3.dto;

public record AvisDTO(
        Integer id,

        String message,

        Float note,

       UserDTO user,

        ProductDTO product
) {
}
