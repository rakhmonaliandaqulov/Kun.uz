package com.example.repository;

import com.example.entity.CommentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, String>{
    @Modifying
    @Transactional
    @Query("update CommentEntity  set content =:content where id=:id")
    void updateContent(@Param("content") String content, @Param("id") Integer commentId);

    @Modifying
    @Transactional
    @Query("update CommentEntity set visible=:visible where id=:id")
    Integer updateVisible(@Param("visible") boolean visible, @Param("id") Integer id);

    List<CommentEntity> getByVisible(Boolean visible);
    @Modifying
    @Transactional
    @Query("update CommentEntity set visible=false where replyId=?1")
    void deleteReplyIdComment(Integer id);
}
