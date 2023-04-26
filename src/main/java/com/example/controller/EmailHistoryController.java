package com.example.controller;

import com.example.dto.category.CategoryDto;
import com.example.dto.emailHistory.EmailHistoryDto;
import com.example.service.EmailHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/v1/email_history")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;

    @GetMapping("/get-by/email/{email}")
    public ResponseEntity<EmailHistoryDto> getByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    @GetMapping("/get-by/date/{date}")
    public ResponseEntity<List<EmailHistoryDto>> getByEmail(@PathVariable("date") LocalDate date){
        return ResponseEntity.ok(emailHistoryService.getByDate(date));
    }
    @GetMapping("/list-paging")
    public ResponseEntity<Page<EmailHistoryDto>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "2") int size) {
        return ResponseEntity.ok(emailHistoryService.getAll(page, size));
    }
}
