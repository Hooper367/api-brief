package com.apibrief3.dto;

import java.util.List;

public record UserDTO(
        Integer id,
        String firstName,

        String lastName,

        String email,

        List<AdressDTO> adresses

){
}
