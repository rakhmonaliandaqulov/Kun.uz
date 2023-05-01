package com.example.exps;

public class ArticleNotFoundException extends Throwable {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
