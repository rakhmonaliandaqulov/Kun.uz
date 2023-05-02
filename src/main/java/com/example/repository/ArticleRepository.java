package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.enums.ArticleStatus;
import com.example.mapper.ArticleShortInfoMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    @Transactional
    @Modifying
    @Query("update  ArticleEntity  set status = :status where id =:id")
    int changeStatus(@Param("status") ArticleStatus status, @Param("id") String id);

//    @Query("FROM ArticleEntity WHERE type.id =:articleTypeId ORDER BY createdDate DESC limit 5")
//    List<ArticleEntity> findLastFiveArticleByType(@Param("articleTypeId") Integer articleTypeId);

    List<ArticleEntity> findTop5ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(Integer typeId, ArticleStatus published, boolean b);

    @Query("From ArticleEntity where status =:status and visible = true and typeId =:typeId order by createdDate desc limit 5")
    List<ArticleEntity> find5ByTypeId(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);
//
//    @Query("SELECT new ArticleEntity(id,title,description,attachId,publishedDate) From ArticleEntity where status =:status and visible = true and typeId =:typeId order by createdDate desc limit 5")
//    List<ArticleEntity> find5ByTypeId2(@Param("typeId") Integer typeId, @Param("status") ArticleStatus status);

    List<ArticleEntity> findTop3ByTypeIdAndStatusAndVisibleOrderByCreatedDateDesc(Integer typeId, ArticleStatus published, boolean b);

    @Query(value = "SELECT a.id,a.title,a.description,a.attach_id,a.published_date " +
            " FROM article AS a  where  a.type_id =:t_id and status =:status order by created_date desc Limit :limit",
            nativeQuery = true)
    List<ArticleShortInfoMapper> getTopNative(@Param("t_id") Integer typeId,
                                              @Param("status") String status,
                                              @Param("limit") Integer limit);

    @Query("from ArticleEntity where status = :status and visible = true")
    List<ArticleEntity> getAllArticle(@Param("status") ArticleStatus published);

    @Query("from ArticleEntity where id =:id and status =:status ")
    Optional<ArticleEntity> getById(@Param("id") String id, @Param("status") ArticleStatus status);


    @Query(value = "SELECT a.id , a.title,a.description,a.attach_id,a.published_date" +
            " FROM article AS a  inner join article_type as t on t.id = a.type_id where  a.id != :id order by a.created_date desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> findAll4(@Param("id") String id, @Param("limit") Integer limit);

    @Query(value = "SELECT a.id , a.title,a.description,a.attach_id,a.published_date" +
            " FROM article AS a inner join article_type as t on t.id = a.type_id order by a.view_count desc Limit :limit", nativeQuery = true)
    List<ArticleShortInfoMapper> find4(@Param("limit") Integer limit);



//    @Query(value = "SELECT a.id , a.title,a.description,a.attach_id,a.published_date" +
//            " FROM article AS a inner join article_type as t on t.id = a.type_id where a.region_id :region_id and a.type_id :type_id order by a.created_date desc Limit :limit", nativeQuery = true)
//    List<ArticleShortInfoMapper> find5ByTypeAndRegion(@Param("limit") Integer limit, @Param("region_id") Integer regionId, @Param("type_id") Integer typeId);

    @Query("from ArticleEntity where typeId =:typeId and regionId =:regionId order by createdDate limit 5")
    List<ArticleEntity> find5ByTypeAndRegion(@Param("typeId") Integer typeId, @Param("regionId") Integer regionId);


    @Query("from ArticleEntity where categoryId =:categoryId order by createdDate limit 5")
    List<ArticleEntity> get5ByCategoryId(@Param("categoryId") Integer categoryId);

    Page<ArticleEntity> findAllByRegionId(Pageable paging, Integer id);

    Page<ArticleEntity> findAllByCategoryId(Pageable paging, Integer id);


    @Transactional
    @Modifying
    @Query("update  ArticleEntity  set viewCount = :viewCount where id =:id")
    Integer updateViewCount(@Param("viewCount") Integer viewCount, @Param("id") String id);
}
