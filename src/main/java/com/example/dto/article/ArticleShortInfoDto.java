package com.example.dto.article;

import com.example.dto.attach.AttachDto;
import com.example.entity.AttachEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleShortInfoDto {
    private String id;
    private String title;
    private String description;
    private AttachDto image;
    private LocalDateTime publishedDate;
}
