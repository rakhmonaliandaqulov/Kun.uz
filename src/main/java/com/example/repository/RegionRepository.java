package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.entity.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer>{
    Page<RegionEntity> findAll(Pageable paging);
}
