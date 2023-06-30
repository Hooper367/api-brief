package com.apibrief3.record.userRequest;

import com.apibrief3.dto.UserDTO;

public record AddAddresstoUserRequest(
        Integer number,

        String street,

        String country,

        String zipCode,

        String city,

        Integer userId
) {
}
