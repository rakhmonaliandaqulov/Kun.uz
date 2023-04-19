package com.example.controller;

import com.example.dto.ArticleTypeDto;
import com.example.dto.JwtDto;
import com.example.dto.RegionDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ArticleTypeService;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping({"", "/"})
    public ResponseEntity<Integer> create(@RequestBody RegionDto dto,
                                          @RequestHeader("Authorization") String authorization) {
        String[] str = authorization.split(" ");
        String jwt = str[1];
        JwtDto jwtDTO = JwtUtil.decode(jwt);
        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new MethodNotAllowedException("Method not allowed");
        }
        return ResponseEntity.ok(regionService.create(dto, jwtDTO.getId()));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody RegionDto articleTypeDto) {
        return ResponseEntity.ok(regionService.update(id, articleTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable ("id") Integer id) {
        return ResponseEntity.ok(regionService.deleteById(id));
    }

    @GetMapping("/list-paging")
    public ResponseEntity<Page<RegionDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        return ResponseEntity.ok(regionService.getAll(page, size));
    }
}
