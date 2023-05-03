package com.example.repository;

import com.example.entity.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {

}
