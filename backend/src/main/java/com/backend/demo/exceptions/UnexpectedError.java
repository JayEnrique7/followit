package com.backend.demo.exceptions;

public class UnexpectedError extends RuntimeException {
    public UnexpectedError(String message) {
        super(message);
    }
}
