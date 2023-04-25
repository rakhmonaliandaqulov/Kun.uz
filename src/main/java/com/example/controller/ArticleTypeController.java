package com.example.controller;

import com.example.dto.articleType.ArticleTypeDto;
import com.example.dto.articleType.ArticleTypeLangDto;
import com.example.dto.JwtDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ArticleTypeService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article-type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping({"", "/"})
    public ResponseEntity<Integer> create(@RequestBody ArticleTypeDto dto,
                                          @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(articleTypeService.create(dto, jwtDTO.getId()));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable ("id") Integer id,
                                          @RequestBody ArticleTypeDto articleTypeDto) {
        return ResponseEntity.ok(articleTypeService.update(id, articleTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable ("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.deleteById(id));
    }

    @GetMapping("/list-paging")
    public ResponseEntity<Page<ArticleTypeDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        return ResponseEntity.ok(articleTypeService.getAll(page, size));
    }

    @GetMapping("/getLang/{lang}")
    public ResponseEntity<List<ArticleTypeLangDto>> getLang(@PathVariable ("lang") String lang) {
        return ResponseEntity.ok(articleTypeService.getLang(lang));
    }
}
