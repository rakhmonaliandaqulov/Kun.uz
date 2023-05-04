package com.example.exps;

public class ItemAlreadyExistsException extends Throwable {
    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}
