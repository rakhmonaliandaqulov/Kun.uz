package com.example.dto.emailHistory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class EmailHistoryDto {
    private Integer id;
    private String message;
    private String email;
    private LocalDate createdDate;
}
