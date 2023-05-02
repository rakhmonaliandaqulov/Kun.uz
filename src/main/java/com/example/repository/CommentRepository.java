package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, String>{
    CommentEntity getById(String id);

    List<CommentEntity> getByArticleId(String article_id);
}
