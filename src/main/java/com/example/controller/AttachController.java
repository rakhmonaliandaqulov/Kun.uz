package com.example.controller;

import com.example.dto.JwtDto;
import com.example.dto.attach.AttachDto;
import com.example.enums.ProfileRole;
import com.example.service.AttachService;
import com.example.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDto> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(attachService.saveToSystem3(file));
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadImage2(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }


    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general2(fileName);
    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        Resource file = (Resource) attachService.download(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id){
        return ResponseEntity.ok(attachService.delete(id));
    }

    @GetMapping("/list-paging")
    public ResponseEntity<Page<AttachDto>> getAllWithPagination(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestHeader("Authorization") String authorization) {
        JwtDto jwtDTO = JwtUtil.getJwtDTO(authorization, ProfileRole.ADMIN);

        return ResponseEntity.ok(attachService.getAll(page, size));
    }


}
