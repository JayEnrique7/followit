package com.backend.demo.exceptions;

public class UnacceptableException extends RuntimeException {
    public UnacceptableException(String message) {
        super(message);
    }
}
