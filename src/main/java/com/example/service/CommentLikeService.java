package com.example.service;

import com.example.dto.comment.CommentLikeDto;
import com.example.entity.ArticleLikeEntity;
import com.example.entity.CommentLikeEntity;
import com.example.enums.EmotionStatus;
import com.example.repository.ArticleLikeRepository;
import com.example.repository.CommentLikeRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    public boolean like(String articleId, Integer profileId) {
        makeEmotion(articleId, profileId, EmotionStatus.LIKE);
        return true;
    }

    public boolean dislike(String articleId, Integer profileId) {
        makeEmotion(articleId, profileId, EmotionStatus.DISLIKE);
        return true;
    }

    public boolean delete(String articleId, Integer profileId) {
        commentLikeRepository.delete(articleId, profileId);
        return true;
    }

    private void makeEmotion(String articleId, Integer profileId, EmotionStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository
                .findByArticleIdAndProfileId(articleId, profileId);
        if (optional.isEmpty()) {
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setArticleId(articleId);
            entity.setProfileId(profileId);
            entity.setStatus(status);
            commentLikeRepository.save(entity);
            // article like count dislike count larni trigger orqali qilasizlar.
        } else {
            commentLikeRepository.update(status, articleId, profileId);
        }
    }
}
