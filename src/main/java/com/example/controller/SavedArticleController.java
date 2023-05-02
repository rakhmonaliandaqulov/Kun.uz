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
    @PostMapping("/create")
    public ResponseEntity<Integer> create(@RequestBody SavedArticleDto articleSavedDto){
        return ResponseEntity.ok(savedArticleService.create(articleSavedDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(savedArticleService.delete(id));
    }

    @GetMapping("/getList")
    public ResponseEntity<List<SavedArticleResponseDto>> getList() {
         return ResponseEntity.ok(savedArticleService.getList());
    }

}
