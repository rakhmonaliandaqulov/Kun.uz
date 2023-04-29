package com.example.dto.articleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ArticleTypeDto {
    private Integer id;
    @NotNull(message = "nameUz required")
    private String nameUz;
    @NotBlank(message = "nameRu required")
    private String nameRU;
    @NotBlank(message = "nameRu required")
    private String nameEng;
    private LocalDateTime createdDate;
    private Boolean visible;
}

