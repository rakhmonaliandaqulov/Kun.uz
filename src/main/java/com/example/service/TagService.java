package com.example.service;

import com.example.dto.TagDto;
import com.example.entity.TagEntity;
import com.example.exps.ItemAlreadyExistsException;
import com.example.exps.ItemNotFoundException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDto create(TagDto dto) throws ItemAlreadyExistsException {
        TagEntity old = tagRepository.getByName(dto.getName());
        if (old != null) {
            throw new ItemAlreadyExistsException("Item already exist");
        }
        TagEntity entity = new TagEntity();
        entity.setName(dto.getName());
        entity = tagRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public TagDto update(Integer tagId, TagDto dto) {
        TagEntity entity = getById(tagId);
        entity.setName(dto.getName());
        return toDTO(tagRepository.save(entity));
    }

    public TagEntity getById(Integer tagId) {
        TagEntity entity = tagRepository.findById(tagId).orElseThrow(() -> {
            throw new ItemNotFoundException("Item not found");
        });
        return entity;
    }

    public TagDto toDTO(TagEntity entity) {
        TagDto dto = new TagDto();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        return dto;
    }

    public List<TagDto> toList(List<TagEntity> list) {
        List<TagDto> dtoArrayList = new ArrayList<>();
        list.forEach(tag -> {
            dtoArrayList.add(toDTO(tag));
        });
        return dtoArrayList;
    }

    public void delete(Integer tagId) {
        tagRepository.deleteById(tagId);
    }

    public Page<TagDto> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TagEntity> entityPage = tagRepository.findAll(pageable);
        return new PageImpl<>(toList(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

    public TagDto getByName(String tagName) {
        return toDTO(tagRepository.getByName(tagName));
    }
}
