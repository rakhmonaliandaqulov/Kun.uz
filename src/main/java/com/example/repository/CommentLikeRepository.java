package com.example.repository;

import com.example.entity.CommentEntity;
import org.hibernate.validator.constraints.ru.INN;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentEntity, Integer> {
}
