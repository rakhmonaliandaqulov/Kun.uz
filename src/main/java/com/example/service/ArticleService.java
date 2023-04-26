package com.example.service;

import com.example.dto.article.ArticleDto;
import com.example.entity.ArticleEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.exps.AppBadRequestException;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    public Integer create(ArticleDto dto, Integer id) {
        RegionEntity student = regionService.get(dto.getRegionId());
        if (student == null) {
            throw new AppBadRequestException("Region not found: " + dto.getRegionId());
        }
        CategoryEntity course = categoryService.get(dto.getCategoryId());
        if (course == null) {
            throw new AppBadRequestException("Category not found: " + dto.getCategoryId());
        }

        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setSharedCount(dto.getSharedCount());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setVisible(true);
        entity.setStatus(ArticleStatus.NOTPUBLISHED);
        entity.setCreated_date(LocalDateTime.now());
        entity.setVisible(Boolean.TRUE);
        entity.setModeratorId(3);
        articleRepository.save(entity); // save profile

        dto.setId(entity.getId());
        return entity.getId();
    }
}
