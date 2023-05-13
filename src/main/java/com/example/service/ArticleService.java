package com.example.service;

import com.example.dto.TagDto;
import com.example.dto.article.*;
import com.example.dto.articleType.ArticleTypeDto;
import com.example.dto.category.CategoryDto;
import com.example.dto.region.RegionDto;
import com.example.entity.*;
import com.example.enums.ArticleStatus;
import com.example.enums.LangEnum;
import com.example.exps.ItemNotFoundException;
import com.example.mapper.ArticleShortInfoMapper;
import com.example.repository.ArticleFilterRepository;
import com.example.repository.ArticleRepository;
import com.example.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagService tagService;
    @Autowired
    private AttachService attachService;
    @Autowired
    private ArticleFilterRepository articleFilterRepository;

    public ArticleDto create(ArticleDto dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setModeratorId(SpringSecurityUtil.getProfileId());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setTypeId(dto.getTypeId());
        entity.setId(UUID.randomUUID().toString());
        entity = articleRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public ArticleDto update(ArticleDto dto, String articleId) {
        ArticleEntity entity = getById(articleId);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setRegionId(dto.getRegionId());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);
        articleRepository.save(entity);
        dto.setId(articleId);
        return dto;
    }


    private ArticleDto toDTO(ArticleEntity entity) {

        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
//        dto.setSharedCount(entity.getSharedCount());
        dto.setAttachId(entity.getAttachId());
        dto.setRegionId(entity.getRegion().getId());
        dto.setCategoryId(entity.getCategory().getId());
//        dto.setModeratorId(entity.getModerator().getId());
//        dto.setPublisherId(entity.getPublisher().getId());
//        dto.setStatus(entity.getStatus());
//        dto.setCreatedDate(entity.getCreatedDate());
//        dto.setPublishedDate(entity.getPublishedDate());
//        dto.setVisible(entity.getVisible());
//        dto.setViewCount(entity.getViewCount());
        return dto;
    }

    //    private ArticleFullInfoDTO toFullInfoDTO(ArticleEntity entity) {
//        ArticleFullInfoDTO dto = new ArticleFullInfoDTO();
//        dto.setId(entity.getId());
//        dto.setTitle(entity.getTitle());
//        dto.setDescription(entity.getDescription());
//        dto.setContent(entity.getContent());
//        dto.setPublishedDate(entity.getPublishedDate());
//        dto.setViewCount(entity.getViewCount());
//        dto.setAttachId(entity.getAttachId());
//        dto.setRegionId(entity.getRegionId());
//        dto.setCategoryId(entity.getCategoryId());
//        dto.setTypeId(entity.getTypeId());
//        return dto;
//    }
    private ArticleFullInfoDto toFullInfoDto(ArticleEntity entity, LangEnum langEnum) {

        ArticleFullInfoDto dto = new ArticleFullInfoDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setViewCount(entity.getViewCount());
        dto.setAttach(attachService.getAttachLink(entity.getAttachId()));

        CategoryDto categoryDTO = new CategoryDto();
        categoryDTO.setId(entity.getCategoryId());

        RegionDto regionDTO = new RegionDto();
        regionDTO.setId(entity.getRegionId());

        ArticleTypeDto articleTypeDTO = new ArticleTypeDto();
        articleTypeDTO.setId(entity.getTypeId());
        switch (langEnum) {
            case ENG -> {
                regionDTO.setNameEng(entity.getRegion().getNameEng());
                categoryDTO.setNameEng(entity.getCategory().getNameEng());
                articleTypeDTO.setNameEng(entity.getType().getNameEng());
            }
            case RU -> {
                regionDTO.setNameRU(entity.getRegion().getNameRu());
                categoryDTO.setNameRU(entity.getCategory().getNameRu());
                articleTypeDTO.setNameRU(entity.getType().getNameRu());

            }
            case UZ -> {
                regionDTO.setNameUz(entity.getRegion().getNameUz());
                categoryDTO.setNameUz(entity.getCategory().getNameUz());
                articleTypeDTO.setNameUz(entity.getType().getNameUz());

            }

        }
        dto.setRegion(regionDTO);
        dto.setCategory(categoryDTO);
        dto.setType(articleTypeDTO);
        return dto;
    }
    /*ArticleFullInfo
    id(uuid),title,description,content,shared_count,
    region(key,name),category(key,name),published_date,view_count,like_count,
    tagList(name)*/
    public int delete(String id) {
        return articleRepository.updateVisible(false, id);
    }
    private ArticleEntity getById(String id) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        if (optional.get().getVisible() == false) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }

    public Boolean changeStatusToPublish(String id, ArticleStatus status) {
        ArticleEntity entity = getById(id);
        if (status.equals(ArticleStatus.PUBLISHED)) {
            entity.setPublishedDate(LocalDateTime.now());
            entity.setPublisherId(SpringSecurityUtil.getProfileId());
        }
        entity.setStatus(status);
        articleRepository.save(entity);
        return true;
    }

    public Object getTop5ByTypeId(Integer typeId) {
        List<ArticleShortInfoMapper> entityList = articleRepository.getTopN(typeId, ArticleStatus.PUBLISHED.name(), 5);
        return toShortInfo(entityList);
    }

    public Object getTop3ByTypeId(Integer typeId) {
        List<ArticleShortInfoMapper> entityList = articleRepository.getTopN(typeId, ArticleStatus.PUBLISHED.name(), 3);
        return toShortInfo(entityList);
    }

    public Object getTop8ByTypeId(Integer typeId) {
        List<ArticleShortInfoMapper> entityList = articleRepository.getTopN(typeId, ArticleStatus.PUBLISHED.name(), 8);
        return toShortInfo(entityList);
    }

    private List<ArticleShortInfoDto> toShortInfo(List<ArticleShortInfoMapper> entityList) {
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleShortInfoDto dto = new ArticleShortInfoDto();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublished_date());
            dto.setImage(attachService.getAttachLink(entity.getAttachId()));
            dtoList.add(dto);
        });
        return dtoList;
    }

    private List<ArticleShortInfoDto> toShortInfoList(List<ArticleEntity> entityList) {
        List<ArticleShortInfoDto> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleShortInfoDto dto = new ArticleShortInfoDto();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            dto.setPublishedDate(entity.getPublishedDate());
            dto.setImage(attachService.getAttachLink(entity.getAttachId()));
            dtoList.add(dto);
        });
        return dtoList;
    }

    public ArticleFullInfoDto getByIdAndLang(String articleId, LangEnum lang) {
        ArticleEntity entity = getById(articleId);
        return toFullInfoDto(entity, lang);
    }

    public Object getByTypeWithoutId(String articleId, Integer typeId) {
        List<ArticleShortInfoMapper> entityList = articleRepository.getTopNWithoutId(typeId, articleId, 4);
        return toShortInfo(entityList);
    }

    public Object getByTop4Read() {
        List<ArticleShortInfoMapper> entityList = articleRepository.getTopNRead(4);
        return toShortInfo(entityList);
    }

    public Object getTop5TypeAndRegion(Integer regionId, Integer typeId) {
        List<ArticleShortInfoMapper> entityList = articleRepository.getTopNTypeAndRegion(typeId, regionId, 5);
        return toShortInfo(entityList);
    }

    public Page<ArticleShortInfoDto> getArticleByRegion(Integer regionId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "view_count");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleShortInfoMapper> entityList = articleRepository.getArticleByRegion(regionId, pageable);
        return new PageImpl<ArticleShortInfoDto>(toShortInfo(entityList.getContent()), pageable, entityList.getTotalElements());
    }

    public Page<ArticleShortInfoDto> getArticleByCategory(Integer categoryId, Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "view_count");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<ArticleShortInfoMapper> entityList = articleRepository.getArticleByCategory(categoryId, pageable);
        return new PageImpl<ArticleShortInfoDto>(toShortInfo(entityList.getContent()), pageable, entityList.getTotalElements());
    }

    public Object filter(ArticleFilterDto dto) {
        List<ArticleEntity> entityList = articleFilterRepository.filter(dto);
        return toShortInfoList(entityList);
    }

    public Object getByTagName(String tagName) {
        TagDto tagDTO = tagService.getByName(tagName);
        return null;

    }
}
