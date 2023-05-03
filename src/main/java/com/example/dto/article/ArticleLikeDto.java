package com.example.dto.article;

import com.example.enums.LikeStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleLikeDto {
    private Integer id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;
    private LikeStatus status;
}
