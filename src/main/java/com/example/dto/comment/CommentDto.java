package com.example.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Integer id;
    @Positive(message = "profile Id must be greater  than zero")
    private Integer profileId;
    @NotBlank(message = "content must  have some value")
    private String content;
    @NotBlank(message = "articleId must have some value")
    private String articleId;
    private Integer replyId;
    private LocalDateTime createdDate=LocalDateTime.now();
    private LocalDateTime updateDate;
    private Boolean visible;
}
