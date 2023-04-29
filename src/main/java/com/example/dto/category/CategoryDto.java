package com.example.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CategoryDto {
    private Integer id;
    @NotNull(message = " nameUz required")
    private String nameUz;
    @NotNull(message = " nameRu required")
    private String nameRU;
    @NotNull(message = " nameEng required")
    private String nameEng;
    private LocalDateTime createdDate;
    private Boolean visible;
}
