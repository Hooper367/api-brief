package com.apibrief3.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CommandDTO(
        Integer id,
        LocalDateTime dateCommand,
        UserDTO user,
        CommandDetailsDTO commandDetails
) {
}
