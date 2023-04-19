package com.example.repository;

import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    Page<CategoryEntity> findAll(Pageable paging);
}
