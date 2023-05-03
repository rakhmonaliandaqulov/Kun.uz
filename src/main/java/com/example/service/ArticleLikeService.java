package com.example.service;

import com.example.dto.article.ArticleLikeDto;
import com.example.repository.ArticleLikeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public Boolean like(Integer id, ArticleLikeDto dto) {
        return null;
    }

    public Boolean disLike(Integer id, ArticleLikeDto dto) {
        return null;
    }

    public Boolean remove(Integer id) {
        return null;
    }
}
