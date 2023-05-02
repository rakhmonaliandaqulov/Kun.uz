package com.example.service;

import com.example.dto.article.SavedArticleDto;
import com.example.entity.SavedArticleEntity;
import org.springframework.stereotype.Service;

@Service
public class SavedArticleService {

    public Integer create(Integer id, SavedArticleDto dto) {
        SavedArticleEntity entity = get(id);
    }
}
