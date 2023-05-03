package com.example.dto.comment;

import com.example.enums.CommentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentLikeDto {
    private Integer id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;
    private CommentStatus status;
}
