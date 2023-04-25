package com.example.controller;

import com.example.dto.auth.AuthDto;
import com.example.dto.auth.AuthResponseDto;
import com.example.dto.ProfileDto;
import com.example.dto.auth.RegistrationDto;
import com.example.dto.auth.RegistrationResponseDto;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/registration")
    public ResponseEntity<ProfileDto> registsation(@RequestBody ProfileDto dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDto> registration(@RequestBody RegistrationDto dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/email/verification/{jwt}")
    public ResponseEntity<RegistrationResponseDto> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
}
