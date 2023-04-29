package com.example.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryLangDto {
    private Integer id;
    @NotNull(message = " name required")
    private String name;
}
