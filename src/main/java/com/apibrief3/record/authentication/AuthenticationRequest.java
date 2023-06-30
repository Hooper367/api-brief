package com.apibrief3.record.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotEmpty(message = "email cannot be empty")
        @NotNull(message = "email cannot be null")
        @Email(message = "email must correspond to email format")
        String email,

        @NotEmpty(message = "password cannot be empty")
        @NotNull(message = "password cannot be null")
        String password

) {
}
