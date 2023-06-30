package com.apibrief3.record.userRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRegistrationRequest(
        @NotEmpty(message = "email cannot be empty")
        @NotNull(message = "email cannot be null")
        @Email(message = "email must correspond to email format")
        String email,

        @NotNull(message = "first name cannot be null")
        @NotEmpty(message = "first name cannot be empty")
        String firstName,

        @NotNull(message = "last name cannot be null")
        @NotEmpty(message = "last name cannot be empty")
        String lastName,


        @NotEmpty(message = "password cannot be empty")
        @NotNull(message = "password cannot be null")
        @Pattern(regexp ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>.]).{8,}$" ,
                message = "Password must contains at least 8 character that includes Uppercase, LowerCase, Numbers and a special character")
        String password
){
}
