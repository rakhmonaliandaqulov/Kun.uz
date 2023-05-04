package com.example.repository;

import com.example.dto.article.ArticleFilterDto;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleShortInfoMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    @Autowired
    private EntityManager entityManager;
    Optional<ArticleEntity> findByTitle(String title);
    Optional<ArticleEntity> findById(String id);
    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = :visible where id = :id")
    int updateVisible(@Param("visible") Boolean visible, @Param("id") String id);


    @Transactional
    @Modifying
    @Query("update ArticleEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ArticleStatus status, @Param("id") String id);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status =:status order by created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> getTopN(@Param("t_id") Integer t_id, @Param("status") String status, @Param("limit") Integer limit);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status ='PUBLISHED' and a.id<>:a_id order by created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> getTopNWithoutId(@Param("t_id") Integer t_id, @Param("a_id") String a_id, @Param("limit") Integer limit);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where status ='PUBLISHED'  order by view_count desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> getTopNRead(@Param("limit") Integer limit);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and a.region_id =:r_id and status ='PUBLISHED'  order by created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> getTopNTypeAndRegion(@Param("t_id") Integer t_id, @Param("r_id") Integer r_id, @Param("limit") Integer limit);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where a.region_id =:r_id and status ='PUBLISHED'  order by created_date desc  ",
            countQuery = "SELECT count(*) from article ", nativeQuery = true)
    Page<ArticleShortInfoMapper> getArticleByRegion(@Param("r_id") Integer regionId, Pageable pageable);
    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where a.category_id =:c_id and status ='PUBLISHED'  order by created_date desc  ",
            countQuery = "SELECT count(*) from article ", nativeQuery = true)
    Page<ArticleShortInfoMapper> getArticleByCategory(@Param("c_id")Integer categoryId, Pageable pageable);

}
