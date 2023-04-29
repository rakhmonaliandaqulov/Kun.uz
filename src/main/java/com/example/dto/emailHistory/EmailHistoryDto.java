package com.example.dto.emailHistory;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class EmailHistoryDto {
    private Integer id;
    @NotNull(message = "message required")
    private String message;
    @NotNull(message = "email required")
    private String email;
    private LocalDate createdDate;
}
