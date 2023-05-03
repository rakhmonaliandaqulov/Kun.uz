package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikeRepository extends CrudRepository<ArticleLikeEntity, Integer> {
}
