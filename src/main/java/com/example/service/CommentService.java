package com.example.service;

import com.example.dto.comment.CommentDto;
import com.example.entity.ArticleEntity;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.exps.ItemNotFoundException;
import com.example.exps.MethodNotAllowedException;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentDto create(CommentDto dto, Integer creater_id) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(creater_id);
        entity.setReplyId(dto.getReplyId());
        commentRepository.save(entity);
        dto.setId(commentRepository.save(entity).getId());
        return dto;
    }

    public CommentDto update(CommentDto dto, Integer commentId, Integer user_id) {
        CommentEntity entity = getById(commentId);
        if (!entity.getProfileId().equals(user_id)){
            throw new MethodNotAllowedException("Method not allowed");
        }
        entity.setContent(dto.getContent());
        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);
        return toDTO(entity);
    }

    private CommentEntity getById(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(String.valueOf(id));
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found");
        }
        if (optional.get().getVisible() == false) {
            throw new ItemNotFoundException("Item not found");
        }
        return optional.get();
    }

    private CommentDto toDTO(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setReplyId(entity.getReplyId());
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setArticleId(entity.getArticleId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private List<CommentDto> toList(List<CommentEntity> list) {
        List<CommentDto> dtoList = new ArrayList<>();
        list.forEach(commentEntity -> dtoList.add(toDTO(commentEntity)));
        return dtoList;
    }

    public Integer delete(Integer id) {
        int res= commentRepository.updateVisible(false, id);
        commentRepository.deleteReplyIdComment(id);
        return res;
    }

    public List<CommentDto> list() {
        return toList(commentRepository.getByVisible(true));
    }

}
