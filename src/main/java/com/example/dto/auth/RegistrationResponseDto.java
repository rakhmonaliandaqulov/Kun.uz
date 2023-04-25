package com.example.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponseDto {
    private String message;

    public RegistrationResponseDto(String message) {
        this.message = message;
    }

}
