package com.example.dto.auth;

import com.example.enums.ProfileRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDto {
    @NotNull(message = " name required")
    private String name;
    @NotNull(message = " surname required")
    private String surname;
    private ProfileRole role;
    private String jwt;
}
