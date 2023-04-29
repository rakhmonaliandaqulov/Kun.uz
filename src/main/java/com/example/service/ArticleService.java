package com.example.service;

import com.example.dto.article.ArticleDto;
import com.example.dto.article.ArticleRequestDto;
import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.example.exps.AppBadRequestException;
import com.example.exps.ItemNotFoundException;
import com.example.repository.ArticleRepository;
import com.example.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final CategoryService categoryService;


    public ArticleRequestDto create(ArticleRequestDto dto, Integer moderId) {
        // check
//        ProfileEntity moderator = profileService.get(moderId);
//        RegionEntity region = regionService.get(dto.getRegionId());
//        CategoryEntity category = categoryService.get(dto.getCategoryId());
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(moderId);
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        // type
        articleRepository.save(entity);
        return dto;
    }


    public ArticleRequestDto update(ArticleRequestDto dto, String id) {
        ArticleEntity entity = get(id);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);
        return dto;
    }

    public boolean delete(Integer id) {
//        ArticleEntity entity = articleRepository.findById(id).orElse(null);
//        if (entity == null) {
//            throw new RuntimeException("this article is null");
//        }
//        entity.setVisible(false);
//        articleRepository.save(entity);
        return true;
    }

    public String changeStatus(ArticleStatus status, Integer id) {
//        ArticleEntity entity = articleRepository.findById(id).orElse(null);
//        if (entity == null) {
//            throw new RuntimeException("entity is null");
//        }
//        entity.setStatus(status);
//        articleRepository.save(entity);
        return "changed !!! ";
    }

    public ArticleEntity DTOToEntity(ArticleRequestDto dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setContent(dto.getContent());
        entity.setCategory(categoryRepository.findById(dto.getCategoryId()).orElse(null));
        entity.setDescription(dto.getDescription());
//        entity.setSharedCount(dto.getSharedCount());
        return entity;
    }


    public ArticleEntity get(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found: " + id);
        }
        return optional.get();
    }
}
