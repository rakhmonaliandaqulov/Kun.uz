package com.example.mapper;

import com.example.dto.article.ArticleShortInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedArticleShortInfoDTO {
    private Integer id;
    private ArticleShortInfoDto article;

}
