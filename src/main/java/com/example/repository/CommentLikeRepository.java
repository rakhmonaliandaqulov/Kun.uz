package com.example.repository;

import com.example.entity.ArticleLikeEntity;
import com.example.entity.CommentEntity;
import com.example.entity.CommentLikeEntity;
import com.example.enums.EmotionStatus;
import jakarta.transaction.Transactional;
import org.hibernate.validator.constraints.ru.INN;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentEntity, Integer> {
    Optional<CommentLikeEntity> findByArticleIdAndProfileId(String articleId, Integer profileId);

    @Modifying
    @Transactional
    @Query("update ArticleLikeEntity  set status =:status where articleId=:articleId and profileId=:profileId")
    int update(@Param("status") EmotionStatus status,
               @Param("articleId") String articleId,
               @Param("profileId") Integer profileId);

    @Modifying
    @Transactional
    @Query("delete from ArticleLikeEntity where articleId=:articleId and profileId=:profileId")
    int delete(String articleId, Integer profileId);


}
