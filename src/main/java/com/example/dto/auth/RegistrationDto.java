package com.example.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {
    @NotEmpty(message = "name required")
    private String name;
    @NotEmpty(message = "surname required")
    private String surname;
    @NotEmpty(message = "email required")
    private String email;
    @NotEmpty(message = "phone required")
    private String phone;
    @NotEmpty(message = "password required")
    private String password;
}
