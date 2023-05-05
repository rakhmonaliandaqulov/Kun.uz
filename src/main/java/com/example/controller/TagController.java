package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.TagDto;
import com.example.entity.RegionEntity;
import com.example.enums.ProfileRole;
import com.example.exps.ItemAlreadyExistsException;
import com.example.service.TagService;
import com.example.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Valid TagDto dto,
                                    @RequestHeader("Authorization") String auth) throws ItemAlreadyExistsException {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody TagDto dto,
                                    @RequestHeader("Authorization") String auth,
                                    @PathVariable("id") Integer tagId) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.update(tagId, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id,
                                    @RequestHeader("Authorization") String auth) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        tagService.delete(id);
        return ResponseEntity.ok(true);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getLIst(@RequestHeader("Authorization") String auth,
                                     @RequestParam(value = "page",defaultValue = "1")Integer page,
                                     @RequestParam(value = "size",defaultValue = "10")Integer size) {
        JwtUtil.getJwtDTO(auth, ProfileRole.MODERATOR, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.getList(page,size));
    }
}
