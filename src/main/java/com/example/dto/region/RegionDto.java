package com.example.dto.region;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class RegionDto {
    private Integer id;
    @NotNull(message = "nameUz required")
    private String nameUz;
    @NotNull(message = "nameRU required")
    private String nameRU;
    @NotNull(message = "nameEng required")
    private String nameEng;
    private LocalDateTime createdDate;
    private Boolean visible;
}
