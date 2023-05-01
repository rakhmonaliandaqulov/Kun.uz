package com.example.service;

import com.example.dto.article.ArticleDto;
import com.example.dto.article.ArticleInfoDto;
import com.example.dto.article.ArticleRequestDto;
import com.example.dto.article.ArticleShortInfoDto;
import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.example.exps.ArticleNotFoundException;
import com.example.exps.ItemNotFoundException;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.ArticleRepository;
import com.example.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileService profileService;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final ArticleTypeService articleTypeService;
    private final AttachService attachService;


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
    public List<ArticleDto> articleShortInfo(Integer typeId) {
        ArticleTypeEntity typeEntity = articleTypeService.get(typeId);
        List<ArticleEntity> entityList = articleRepository.findAllByType(typeEntity);
        List<ArticleDto> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(entityToDTO(entity));
        }
        return dtoList;
    }

    public ArticleDto entityToDTO(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setCategoryId(entity.getCategory().getId());
        dto.setTitle(entity.getTitle());
        dto.setRegionId(entity.getRegion().getId());
        return dto;
    }

    public List<ArticleDto> articleShortInfo(List<Integer> idList) {
        List<ArticleEntity> entityList = articleRepository.articleShortInfo(idList);
        List<ArticleDto> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(entityToDTO(entity));
        }
        return dtoList;
    }

    public ArticleEntity get(String id){
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ArticleNotFoundException("article not found");
        }
        return optional.get();
    }

    public ArticleInfoDto articleFullInfo(String id, String lang) {
        ArticleEntity entity = get(id);
        ArticleInfoDto dto = new ArticleInfoDto();
        dto.setId(entity.getId());
        dto.setRegion(entity.getRegion());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setSharedCount(entity.getSharedCount());
        dto.setViewCount(entity.getViewCount());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setTitle(entity.getTitle());
        return dto;
    }

    public List<ArticleShortInfoDto> articleShortInfo() {
        List<ArticleEntity> entityList = articleRepository.mostReadArticles();
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toShortInfo(entity));
        }
        return dtoList;
    }

    public ArticleShortInfoDto toShortInfo(ArticleEntity entity) {
        ArticleShortInfoDto dto = new ArticleShortInfoDto();
        dto.setAttach(entity.getAttach());
        dto.setId(entity.getId());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());
        return dto;
    }


    public List<ArticleShortInfoDto> getRegionArticle(Integer id, int size, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleEntity> entityPage = articleRepository.findByRegionId(id, pageable);
        long totalCount = entityPage.getTotalElements();
        List<ArticleEntity> entityList = entityPage.getContent();
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toShortInfo(entity));
        }
        return dtoList;
    }

    public List<ArticleShortInfoDto> get5CategoryArticle(Integer id) {
        CategoryEntity category = categoryService.get(id);
        List<ArticleEntity> entityList = articleRepository.get5CategoryArticle(category);
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toShortInfo(entity));
        }
        return dtoList;
    }
    public List<ArticleShortInfoDto> get4ArticleByTypes(Integer typeId, String articleId) {
        ArticleTypeEntity type = articleTypeService.get(typeId);
        List<ArticleEntity> entityList = articleRepository.findByTypeIdAndIdNot(type, articleId);
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            dtoList.add(toShortInfo(entity));
        }
        return dtoList;
    }


    public Boolean changeStatus(ArticleStatus status, String id, Integer prtId) {
        ArticleEntity entity = get(id);
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublishedDate(LocalDateTime.now());
            entity.setPublisherId(prtId);
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        // articleRepository.changeStatus(status, id);
        return true;
    }
    public ArticleShortInfoDto toArticleShortInfo(ArticleEntity entity) {
        ArticleShortInfoDto dto = new ArticleShortInfoDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setImage(attachService.getAttachLink(entity.getAttachId()));
        return dto;
    }
    public List<ArticleShortInfoDto> getLast5ByTypeId(Integer typeId) {
        List<ArticleEntity> entityList = articleRepository.findTop5ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(typeId,
                ArticleStatus.PUBLISHED, true);
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(toArticleShortInfo(entity));
        });
        return dtoList;
    }

    public ArticleShortInfoDto toArticleShortInfo(ArticleShortInfoMapper entity) {
        ArticleShortInfoDto dto = new ArticleShortInfoDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishedDate(entity.getPublished_date());
        dto.setImage(attachService.getAttachLink(entity.getAttachId()));
        return dto;
    }
}
