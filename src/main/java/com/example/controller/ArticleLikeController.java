package com.example.controller;

import com.example.dto.article.ArticleLikeDto;
import com.example.dto.comment.CommentLikeDto;
import com.example.service.ArticleLikeService;
import com.example.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article-like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;
    @PostMapping("/like{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") Integer id,
                                        @RequestBody ArticleLikeDto dto) {
        return ResponseEntity.ok(articleLikeService.like(id, dto));
    }

    @PostMapping("/disLike{id}")
    public ResponseEntity<Boolean> disLike(@PathVariable("id") Integer id,
                                           @RequestBody ArticleLikeDto dto) {
        return ResponseEntity.ok(articleLikeService.disLike(id, dto));
    }

    @DeleteMapping("/remove{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleLikeService.remove(id));
    }
}
