package com.example.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDto {
    @NotNull(message = " email required")
    private String email;
    @NotNull(message = " password required")
    private String password;
}
