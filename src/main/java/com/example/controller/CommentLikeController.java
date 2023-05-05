package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.comment.CommentDto;
import com.example.dto.comment.CommentLikeDto;
import com.example.service.ArticleLikeService;
import com.example.service.CommentLikeService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment-like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @GetMapping("/like/{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") String articleId,
                                        @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentLikeService.like(articleId, jwt.getId()));
    }

    @GetMapping("/dislike/{id}")
    public ResponseEntity<Boolean> dislike(@PathVariable("id") String articleId,
                                           @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentLikeService.dislike(articleId, jwt.getId()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String articleId,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization);
        return ResponseEntity.ok(commentLikeService.delete(articleId, jwt.getId()));
    }
}
