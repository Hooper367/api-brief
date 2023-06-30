package com.apibrief3.record.commandRequest;

import com.apibrief3.dto.ProductDTO;
import com.apibrief3.dto.UserDTO;
import com.apibrief3.model.CommandRow;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record AddCommandRequest(
        LocalDateTime dateCommand,

        List<CommandRow> commandItems,

        Integer billingAdressId,

        Integer deliveryAdressId,

        Integer userId

) {
}
