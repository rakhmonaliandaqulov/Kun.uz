package com.example.controller;

import com.example.dto.category.CategoryDto;
import com.example.dto.category.CategoryLangDto;
import com.example.dto.JwtDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.CategoryService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping({"", "/"})
    public ResponseEntity<Integer> create(@RequestBody CategoryDto dto,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto, jwtDTO.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody CategoryDto categoryDto,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(id, categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.deleteById(id, jwtDTO.getId()));
    }

    @GetMapping("/list-paging")
    public ResponseEntity<Page<CategoryDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "2") int size,
                                                    @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getAll(page, size));
    }

    @GetMapping("/getLang/{lang}")
    public ResponseEntity<List<CategoryLangDto>> getLang(@PathVariable ("lang") String lang) {
        return ResponseEntity.ok(categoryService.getLang(lang));
    }
}
