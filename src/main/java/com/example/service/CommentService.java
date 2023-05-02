package com.example.service;

import com.example.dto.CommentDto;
import com.example.entity.ArticleEntity;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.exps.ItemNotFoundException;
import com.example.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ArticleService articleService;

    public CommentDto create(CommentDto commentDto) {
        ProfileEntity profileEntity = profileService.get(commentDto.getProfile_id());
        ArticleEntity article = articleService.get(commentDto.getArticle_id());

        CommentEntity entity = new CommentEntity();
        entity.setContent(commentDto.getContent());
        entity.setProfile(profileEntity);
        entity.setArticle(article);
        entity.setCreatedDate(LocalDateTime.now());
        commentRepository.save(entity);
        commentDto.setCreatedDate(entity.getCreatedDate());
        commentDto.setId(entity.getId());
        return commentDto;
    }

    public Boolean delete(String id) {
        CommentEntity commentEntity = commentRepository.getById(id);
        if (commentEntity == null){
            throw new ItemNotFoundException("Item not found");
        }
        commentEntity.setVisible(false);

        return true;
    }

    public List<CommentDto> getListById(String id) {
        List<CommentEntity> commentEntity = commentRepository.getByArticleId(id);
        List<CommentDto> commentDtoList = new LinkedList<>();
        for (CommentEntity commentEntity1 : commentEntity){
            CommentDto commentDto = new CommentDto();
            commentDto.setId(commentEntity1.getId());
            commentDto.setContent(commentEntity1.getContent());
            commentDto.setArticle_id(commentEntity1.getArticle().getId());
            commentDto.setProfile_id(commentEntity1.getProfile().getId());
            commentDto.setCreatedDate(commentEntity1.getCreatedDate());
            commentDto.setUpdate_date(commentEntity1.getUpdate_date());
            commentDto.setVisible(commentEntity1.getVisible());
            commentDtoList.add(commentDto);

        }
        return commentDtoList;
    }

}
