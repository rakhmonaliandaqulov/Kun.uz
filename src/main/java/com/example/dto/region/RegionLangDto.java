package com.example.dto.region;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionLangDto {
    private Integer id;
    @NotNull(message = "name required")
    private String name;
}
