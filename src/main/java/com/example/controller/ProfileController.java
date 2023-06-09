package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.ProfileDto;
import com.example.enums.ProfileRole;
import com.example.exps.MethodNotAllowedException;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping({"/adm", "/adm/"})
    public ResponseEntity<ProfileDto> create(@RequestBody ProfileDto dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping("/list-paging")
    public ResponseEntity<Page<ProfileDto>> getAllWithPagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto> getById(@PathVariable("id") Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.out.println(currentPrincipalName);
        return ResponseEntity.ok(profileService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.deleteById(id));
    }

    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<Boolean> updateAdmin(@PathVariable ("id") Integer id,
                                               @RequestBody ProfileDto profileDto,
                                               @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.updateAdmin(id, profileDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable ("id") Integer id,
                                               @RequestBody ProfileDto profileDto) {
        return ResponseEntity.ok(profileService.update(id, profileDto));
    }


}
