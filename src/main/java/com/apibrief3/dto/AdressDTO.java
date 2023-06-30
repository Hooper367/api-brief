package com.apibrief3.dto;

public record AdressDTO(
        Integer id,
        String number,

        String street,

        String zipCode,

        String city,

        UserDTO owner

) {
}
