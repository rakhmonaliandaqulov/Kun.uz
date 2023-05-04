package com.example.mapper;

import com.example.kunuz.dto.ArticleShortInfoDTO;
import com.example.kunuz.entity.ArticleEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedArticleShortInfoDTO {
    private Integer id;
    private ArticleShortInfoDTO article;

}
