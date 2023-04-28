package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.ProfileDto;
import com.example.dto.article.ArticleDto;
import com.example.dto.article.ArticleRequestDto;
import com.example.dto.articleType.ArticleTypeDto;
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
    @PostMapping("/create")
    public ResponseEntity<Integer> create(@RequestBody @Valid ArticleDto dto,
                                          @RequestHeader("Authorization") String authorization) {
        /*String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }*/
       // return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
        return ResponseEntity.ok(articleService.create(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.deleteById(id));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable ("id") Integer id,
                                               @RequestBody ArticleDto dto,
                                               @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.MODERATOR)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.update(id, dto));
    }
    @PutMapping("/change-status/{id}")
    public ResponseEntity<Boolean> changeStatus(@PathVariable ("id") Integer id,
                                               @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.PUBLISHER)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleService.changeStatusById(id));
    }

    @PostMapping("")
    public ResponseEntity<ArticleRequestDto> create(@RequestBody ArticleRequestDto dto,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDto jwt = JwtUtil.getJwtDTO(authorization, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwt.getId()));
    }

}
