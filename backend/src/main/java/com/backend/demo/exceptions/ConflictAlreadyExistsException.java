package com.backend.demo.exceptions;

public class ConflictAlreadyExistsException extends RuntimeException {
    public ConflictAlreadyExistsException(String message) {
        super(message);
    }
}
