package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.article.SavedArticleDto;
import com.example.dto.article.SavedArticleResponseDto;
import com.example.dto.region.RegionDto;
import com.example.enums.ProfileRole;
import com.example.service.SavedArticleService;
import com.example.util.JwtUtil;
import org.hibernate.validator.constraints.ru.INN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/saved-article")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody  SavedArticleDto dto,
                                    @RequestHeader("Authorization") String auth) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(savedArticleService.create(dto, jwtDTO.getId()));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String auth) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(auth);
        savedArticleService.delete(id,jwtDTO.getId());
        return ResponseEntity.ok(true);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestHeader("Authorization") String auth) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(auth);
        return ResponseEntity.ok(savedArticleService.getList(jwtDTO.getId()));
    }
}
