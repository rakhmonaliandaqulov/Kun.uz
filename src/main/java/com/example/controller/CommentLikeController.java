package com.example.controller;

import com.example.dto.comment.CommentDto;
import com.example.dto.comment.CommentLikeDto;
import com.example.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment-like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;
    @PostMapping("/like{id}")
    public ResponseEntity<Boolean> like(@PathVariable("id") Integer id,
                                        @RequestBody CommentLikeDto dto) {
        return ResponseEntity.ok(commentLikeService.like(id, dto));
    }

    @PostMapping("/disLike{id}")
    public ResponseEntity<Boolean> disLike(@PathVariable("id") Integer id,
                                        @RequestBody CommentLikeDto dto) {
        return ResponseEntity.ok(commentLikeService.disLike(id, dto));
    }

    @DeleteMapping("/remove{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(commentLikeService.remove(id));
    }


}
