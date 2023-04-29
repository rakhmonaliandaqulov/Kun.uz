package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.ProfileDto;
import com.example.dto.article.ArticleDto;
import com.example.dto.article.ArticleRequestDto;
import com.example.dto.articleType.ArticleTypeDto;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleRequestDto> create(@RequestBody @Valid ArticleRequestDto dto,
                                                    @RequestHeader("Authorization") String authorization) {
//        JwtDTO jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
//        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
        return ResponseEntity.ok(articleService.create(dto, 1));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ArticleRequestDto> update(@RequestBody ArticleRequestDto dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") String articleId) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Integer id,
                                          @RequestParam String status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id));
    }
}
