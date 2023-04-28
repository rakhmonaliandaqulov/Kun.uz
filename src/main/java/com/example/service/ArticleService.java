package com.example.service;

import com.example.dto.article.ArticleDto;
import com.example.dto.article.ArticleRequestDto;
import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.example.exps.AppBadRequestException;
import com.example.exps.ItemNotFoundException;
import com.example.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProfileService profileService;

    public ArticleRequestDto create(ArticleRequestDto dto, Integer moderId) {
        // check
        ProfileEntity moderator = profileService.get(moderId);
        RegionEntity region = regionService.get(dto.getRegionId());
        CategoryEntity category = categoryService.get(dto.getCategoryId());

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
   /* public Integer create(ArticleDto dto, Integer id) {
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
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(Boolean.TRUE);
        entity.setModeratorId(3);
        articleRepository.save(entity); // save profile

        dto.setId(entity.getId());
        return entity.getId();
    }*/

    public Boolean deleteById(Integer id) {
            ArticleEntity entity = get(id);
            if (entity == null) {
                throw new ItemNotFoundException("Article not found.");
            }
            entity.setVisible(false);
            entity.setModeratorId(entity.getModeratorId());
            articleRepository.save(entity);
            return true;
    }

    public ArticleEntity get(Integer id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Article not found: " + id);
        }
        return optional.get();
    }

   /* public Boolean update(Integer id, ArticleDto dto) {
        ArticleEntity entity = get(id);
        if (entity == null) {
            throw new ItemNotFoundException("Article not found");
        }
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());
        entity.setSharedCount(dto.getSharedCount());
        entity.setImageId(dto.getImageId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);
        return true;
    }*/
    public Boolean changeStatusById(Integer id) {
        ArticleEntity entity = get(id);
        if (entity == null) {
            throw new ItemNotFoundException("Article not found.");
        }
        entity.setStatus(ArticleStatus.PUBLISHED);
        entity.setPublisherId(entity.getPublisherId());
        articleRepository.save(entity);
        return true;
    }
}
