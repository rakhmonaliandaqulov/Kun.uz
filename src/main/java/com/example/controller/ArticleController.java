package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.ProfileDto;
import com.example.dto.article.ArticleDto;
import com.example.dto.article.ArticleInfoDto;
import com.example.dto.article.ArticleRequestDto;
import com.example.dto.article.ArticleShortInfoDto;
import com.example.dto.articleType.ArticleTypeDto;
import com.example.entity.ArticleEntity;
import com.example.enums.ArticleStatus;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ArticleService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<ArticleRequestDto> create(@RequestBody @Valid ArticleRequestDto dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ArticleRequestDto> update(@RequestBody ArticleRequestDto dto,
                                                    @RequestHeader("Authorization") String authorization,
                                                    @PathVariable("id") String articleId) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, articleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id,
                                    @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.delete(id));
    }

    @PostMapping("/change-status/{id}")
    public ResponseEntity<Boolean> changeStatus(@PathVariable("id") String id,
                                          @RequestParam String  status,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.PUBLISHER);
        return ResponseEntity.ok(articleService.changeStatus(ArticleStatus.valueOf(status), id));
    }
    @GetMapping("/{typeId}")
    public ResponseEntity<List<ArticleDto>> articleShortInfo(@PathVariable("typeId") Integer typeId) {
        List<ArticleDto> dtoList = articleService.articleShortInfo(typeId);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{list}")
    public ResponseEntity<List<ArticleDto>> articleShortInfo(@PathVariable List<Integer> list) {
        List<ArticleDto> dtoList = articleService.articleShortInfo(list);
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/get_by_id_and_lang")
    public ResponseEntity<ArticleInfoDto> articleFullInfo(@RequestParam("id") String id,
                                                          @RequestParam("lang") String lang) {
        ArticleInfoDto dto = articleService.articleFullInfo(id, lang);
        return ResponseEntity.ok(dto);
    }
   /* @GetMapping("/4-article-by-types")
    public ResponseEntity<List<ArticleShortInfoDto>> get4ArticleByTypes(@RequestParam("typeId") Integer typeId,
                                                                        @RequestParam("id") String id) {
        List<ArticleShortInfoDto> list = articleService.get4ArticleByTypes(typeId, id);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/4most_read")
    public ResponseEntity<List<ArticleShortInfoDto>> articleShortInfo() {
        List<ArticleShortInfoDto> dtoList = articleService.articleShortInfo();
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/region-article")
    public ResponseEntity<List<ArticleShortInfoDto>> getRegionArticle(@RequestParam("id") Integer id, @RequestParam("size") int size,
                                                                      @RequestParam("page") int page) {
        List<ArticleShortInfoDto> list = articleService.getRegionArticle(id, size, page);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/5-category-article/{id}")
    public ResponseEntity<List<ArticleShortInfoDto>> get5CategoryArticle(@PathVariable Integer id) {
        List<ArticleShortInfoDto> list = articleService.get5CategoryArticle(id);
        return ResponseEntity.ok(list);
    }*/

}
