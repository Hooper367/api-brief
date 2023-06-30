package com.apibrief3.record.commandRequest;

import com.apibrief3.dto.ProductDTO;
import com.apibrief3.dto.UserDTO;


import java.time.LocalDateTime;
import java.util.List;

public record AddCommandRequest(
        LocalDateTime dateCommand,

        List<Integer> productsIds,

        Integer billingAdressId,

        Integer deliveryAdressId,

        Integer userId

) {
}
