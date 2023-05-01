package com.example.controller;

import com.example.exps.AppBadRequestException;
import com.example.exps.ArticleNotFoundException;
import com.example.exps.CustomGlobalExceptionHandler;
import com.example.exps.ItemNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceController {
    @ExceptionHandler({AppBadRequestException.class, ItemNotFoundException.class,
            MetaDataAccessException.class, ArticleNotFoundException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
