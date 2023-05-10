package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.UUID;

@EnableWebSecurity
@Component
public class SecurityConfig {
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // authentication
        // login,password ACTIVE,
        String password = UUID.randomUUID().toString();
        System.out.println("User Pasword mazgi: " + password);

        UserDetails user = User.builder()
                .username("user")
                .password("{bctryp}" + password)
                .roles("USER")
                .build();

        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user));
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // authorization
        // URL ,API  Permission
        // /api/v1/article/private/* - MODERATOR
        // /api/v1/article//private/{id} - POST - MODERATOR
        http.csrf().disable().cors().disable();

        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/*/public/**").permitAll()
                .requestMatchers("/api/v1/*/private").hasRole("ADMIN")
                .requestMatchers("/api/v1/*/private/*").hasAnyRole("ADMIN", "MODERATOR")
                .anyRequest()
                .authenticated().and().httpBasic();
        return http.build();
    }
}
