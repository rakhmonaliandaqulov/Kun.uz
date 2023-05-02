package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.article.SavedArticleDto;
import com.example.dto.region.RegionDto;
import com.example.enums.ProfileRole;
import com.example.service.SavedArticleService;
import com.example.util.JwtUtil;
import org.hibernate.validator.constraints.ru.INN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saved-article")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;
    @PostMapping({"", "/{id}"})
    public ResponseEntity<Integer> create(@RequestBody SavedArticleDto dto,
                                          @PathVariable("id") Integer id) {
        return ResponseEntity.ok(savedArticleService.create(id, dto));
    }

}
