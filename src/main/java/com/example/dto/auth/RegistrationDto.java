package com.example.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDto {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
}
