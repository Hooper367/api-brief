package com.apibrief3.record.userRequest;

import com.apibrief3.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UpdateUserResponse(UserDTO user, String jwtToken, String refreshToken) {
}
