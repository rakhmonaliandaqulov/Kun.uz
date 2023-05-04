package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.comment.CommentDto;
import com.example.service.CommentService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.tokens.CommentToken;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CommentDto dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(commentService.create(dto, jwtDTO.getId()));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody CommentDto dto,
                                    @RequestHeader("Authorization") String auth,
                                    @PathVariable("id") Integer commentId) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(commentService.update(dto, commentId,jwtDTO.getId()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String auth) {
        JwtUtil.checkToAdminOrOwner(auth);
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(commentService.list());
    }
}
