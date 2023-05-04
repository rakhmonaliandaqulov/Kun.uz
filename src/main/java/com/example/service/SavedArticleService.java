package com.example.service;

import com.example.dto.article.ArticleShortInfoDto;
import com.example.dto.article.SavedArticleDto;
import com.example.dto.article.SavedArticleResponseDto;
import com.example.entity.ArticleEntity;
import com.example.entity.ProfileEntity;
import com.example.entity.SavedArticleEntity;
import com.example.exps.ItemAlreadyExistsException;
import com.example.exps.ItemNotFoundException;
import com.example.exps.MethodNotAllowedException;
import com.example.mapper.SavedArticleShortInfoDto;
import com.example.repository.SavedArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleService {
    @Autowired
    private SavedArticleRepository savedArticleRepository;
    @Autowired
    private AttachService attachService;

    public SavedArticleDto create(SavedArticleDto dto, Integer user_id) {
        SavedArticleEntity oldEntity = savedArticleRepository.getByUserIdAndArticleId(user_id, dto.getArticleId());
        if (oldEntity!=null){
            try {
                throw new ItemAlreadyExistsException("Item already exist");
            } catch (ItemAlreadyExistsException e) {
                throw new RuntimeException(e);
            }
        }
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(user_id);
        entity = savedArticleRepository.save(entity);
        dto.setProfileId(user_id);
        dto.setArticleId(entity.getArticleId());
        dto.setId(entity.getId());
        return dto;
    }

    public void delete(Integer id, Integer jwtDTOId) {
        SavedArticleEntity entity = getById(id);
        if (entity.getProfileId() != jwtDTOId) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        savedArticleRepository.deleteById(id);
    }

    private SavedArticleEntity getById(Integer id) {
        Optional<SavedArticleEntity> optional = savedArticleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }

    public List<SavedArticleShortInfoDto> getList(Integer user_id) {
        List<SavedArticleEntity> entityList = savedArticleRepository.getList(user_id);
        return toList(entityList);
    }

    private List<SavedArticleShortInfoDto> toList(List<SavedArticleEntity> entityList) {

        List<SavedArticleShortInfoDto> dtoList = new ArrayList<>();
        entityList.forEach(savedArticleEntity -> dtoList.add(toDTO(savedArticleEntity)));
        return dtoList;
    }

    private SavedArticleShortInfoDto toDTO(SavedArticleEntity savedArticleEntity) {
        SavedArticleShortInfoDto dto = new SavedArticleShortInfoDto();
        dto.setId(savedArticleEntity.getId());
        ArticleShortInfoDto articleShortInfoDTO = new ArticleShortInfoDto();
        articleShortInfoDTO.setTitle(savedArticleEntity.getArticle().getTitle());
        articleShortInfoDTO.setDescription(savedArticleEntity.getArticle().getDescription());
        articleShortInfoDTO.setId(savedArticleEntity.getArticle().getId());
        articleShortInfoDTO.setPublishedDate(savedArticleEntity.getArticle().getPublishedDate());
        articleShortInfoDTO.setImage(attachService.getAttachLink(savedArticleEntity.getArticle().getAttachId()));
        dto.setArticle(articleShortInfoDTO);
        return dto;
    }
}
