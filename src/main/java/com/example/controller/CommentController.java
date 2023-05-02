package com.example.controller;

import com.example.dto.CommentDto;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.create(commentDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id){
        return ResponseEntity.ok(commentService.delete(id));
    }

    @GetMapping("/getList/{id}")
    public ResponseEntity<List<CommentDto>> getListById(@PathVariable("id") String id){
        return ResponseEntity.ok(commentService.getListById(id));
    }
}
