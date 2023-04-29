package com.example.service;

import com.example.dto.articleType.ArticleTypeDto;
import com.example.dto.articleType.ArticleTypeLangDto;
import com.example.entity.ArticleTypeEntity;
import com.example.exps.AppBadRequestException;
import com.example.exps.ItemNotFoundException;
import com.example.repository.ArticleTypeRepository;
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
        entity.setNameUz(articleTypeDto.getNameUz());
        entity.setNameRu(articleTypeDto.getNameRU());
        entity.setNameEng(articleTypeDto.getNameEng());
        articleTypeRepository.save(entity);
        return true;
    }

    public ArticleTypeEntity get(Integer id) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found: " + id);
        }
        return optional.get();
    }

    public Boolean deleteById(Integer id, Integer prtId) {
        ArticleTypeEntity entity = get(id);
        entity.setVisible(false);
        entity.setPrtId(prtId);
        articleTypeRepository.save(entity);
        return true;
    }

    public Page<ArticleTypeDto> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<ArticleTypeEntity> pageObj = articleTypeRepository.findAll(paging);

        long totalCount = pageObj.getTotalElements();

        List<ArticleTypeEntity> entityList = pageObj.getContent();
        List<ArticleTypeDto> dtoList = new LinkedList<>();

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
        return new PageImpl<ArticleTypeDto>(dtoList, paging, totalCount);
    }

    public List<ArticleTypeLangDto> getLang(String lang) {
        List<ArticleTypeLangDto> list = new LinkedList<>();

        switch (lang) {
            case "uz" -> list.addAll(getUzLang());
            case "ru" -> list.addAll(getRuLang());
            case "eng" -> list.addAll(getEngLang());
            case "null" -> throw new ItemNotFoundException("Item not found");
            default -> throw new AppBadRequestException("Error");
        }
        return list;
    }

    private List<ArticleTypeLangDto> getEngLang() {
        List<ArticleTypeLangDto> list = new LinkedList<>();
        Iterable<ArticleTypeEntity> entity = articleTypeRepository.findAll();
        for (ArticleTypeEntity articleTypeEntity : entity) {
            ArticleTypeLangDto dto = new ArticleTypeLangDto();
            dto.setId(articleTypeEntity.getId());
            dto.setName(articleTypeEntity.getNameEng());
            list.add(dto);
        }
        return list;
    }

    private List<ArticleTypeLangDto> getRuLang() {
        List<ArticleTypeLangDto> list = new LinkedList<>();
        Iterable<ArticleTypeEntity> entity = articleTypeRepository.findAll();
        for (ArticleTypeEntity articleTypeEntity : entity) {
            ArticleTypeLangDto dto = new ArticleTypeLangDto();
            dto.setId(articleTypeEntity.getId());
            dto.setName(articleTypeEntity.getNameRu());
            list.add(dto);
        }
        return list;
    }
    private List<ArticleTypeLangDto> getUzLang() {
        List<ArticleTypeLangDto> list = new LinkedList<>();
        Iterable<ArticleTypeEntity> entity = articleTypeRepository.findAll();
        for (ArticleTypeEntity articleTypeEntity : entity) {
            ArticleTypeLangDto dto = new ArticleTypeLangDto();
            dto.setId(articleTypeEntity.getId());
            dto.setName(articleTypeEntity.getNameUz());
            list.add(dto);
        }
        return list;
    }
}
