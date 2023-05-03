package com.example.controller;

import com.example.dto.TagDto;
import com.example.entity.RegionEntity;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @PostMapping("/create")
    private ResponseEntity<Boolean> create() {
        return null;
    }
    @PutMapping("/update")
    private ResponseEntity<Boolean> update() {
        return null;
    }
    @DeleteMapping("/delete")
    private ResponseEntity<Boolean> delete() {
        return null;
    }
    @GetMapping("/getList")
    private ResponseEntity<List<TagDto>> getList() {
        return null;
    }
}
