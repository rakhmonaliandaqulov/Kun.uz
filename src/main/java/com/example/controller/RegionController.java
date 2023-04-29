package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.region.RegionDto;
import com.example.dto.region.RegionLangDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.RegionService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping({"", "/"})
    public ResponseEntity<Integer> create(@RequestBody RegionDto dto,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto, jwtDTO.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable("id") Integer id,
                                          @RequestBody RegionDto regionDto,
                                          @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(id, regionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.deleteById(id, jwtDTO.getId()));
    }

    @GetMapping("/paging")
    public ResponseEntity<Page<RegionDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                                  @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.getAll(page, size));
    }
    @GetMapping("/getLang/{lang}")
    public ResponseEntity<List<RegionLangDto>> getLang(@PathVariable ("lang") String lang) {
        return ResponseEntity.ok(regionService.getLang(lang));
    }
}
