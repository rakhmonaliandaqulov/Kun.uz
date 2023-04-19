package com.example.service;

import com.example.dto.ArticleTypeDto;
import com.example.dto.ProfileDto;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.GeneralStatus;
import com.example.exps.ItemNotFoundException;
import com.example.repository.ArticleTypeRepository;
import com.example.util.MD5Util;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    public Integer create(ArticleTypeDto dto, Integer adminId) {

        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRU());
        entity.setNameEng(dto.getNameEng());
        entity.setCreatedDate(LocalDateTime.now());
        entity.setVisible(true);
        entity.setPrtId(adminId);
        articleTypeRepository.save(entity); // save profile

        dto.setId(entity.getId());
        return entity.getId();
    }
    public Boolean update(Integer id, ArticleTypeDto articleTypeDto) {
        ArticleTypeEntity entity = get(id);
        if (entity == null) {
            throw new ItemNotFoundException("Article not found");
        }
        entity.setNameUz(articleTypeDto.getNameUz());
        entity.setNameRu(articleTypeDto.getNameRU());
        entity.setNameEng(articleTypeDto.getNameEng());

        articleTypeRepository.save(entity);
        return true;
    }
    public ArticleTypeEntity get(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Article not found: " + id);
        }
        return optional.get();
    }

    public Boolean deleteById(Integer id) {
        ArticleTypeEntity entity = get(id);
        if (entity == null) {
            throw new ItemNotFoundException("Profile not found.");
        }
        entity.setVisible(false);
        entity.setPrtId(4);
        articleTypeRepository.save(entity);
        return true;
    }

    public Page<ArticleTypeDto> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<ArticleTypeEntity> pageObj = articleTypeRepository.findAll(paging);

        Long totalCount = pageObj.getTotalElements();

        List<ArticleTypeEntity> entityList = pageObj.getContent();
        List<ArticleTypeDto> dtoList = new LinkedList<>();

        if (!pageObj.equals(null)) {
            for (ArticleTypeEntity entity : entityList) {
                ArticleTypeDto dto = new ArticleTypeDto();
                dto.setId(entity.getId());
                dto.setNameUz(entity.getNameUz());
                dto.setNameRU(entity.getNameRu());
                dto.setNameEng(entity.getNameEng());
                dto.setCreatedDate(entity.getCreatedDate());
                dto.setVisible(entity.getVisible());
                dtoList.add(dto);
            }
            Page<ArticleTypeDto> response = new PageImpl<ArticleTypeDto>(dtoList, paging, totalCount);
            return response;
        }
        throw new ItemNotFoundException("ArticleType is empty");
    }
}