package com.apibrief3.dto;

import java.util.List;

public record CommandDetailsDTO(

        Integer id,

        AdressDTO billingAdress,

        AdressDTO deliveryAdress,

        Float totalPrice,

        List<CommandDetailsProductsDTO> commandDetailsProducts
) {
}
