package com.example.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private String id;
    @NotNull(message = "content required")
    private String content;
    @NotNull(message = "profile_id required")
    private Integer profile_id;
    @NotNull(message = "article_id required")
    private String article_id;
    private LocalDateTime createdDate;
    private LocalDateTime update_date;
    private Boolean visible = true;
}
