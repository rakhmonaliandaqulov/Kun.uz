package com.example.repository;

import com.example.entity.SavedArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, Integer> {
}
