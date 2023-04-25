package com.example.dto.article;

import com.example.enums.ArticleStatus;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ArticleDto {
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Long sharedCount;
    private Integer imageId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private LocalDateTime created_date;
    private LocalDate published_date;
    private Boolean visible;
    private Long view_count;
}
