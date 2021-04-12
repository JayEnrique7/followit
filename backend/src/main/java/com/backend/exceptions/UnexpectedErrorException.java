package com.backend.exceptions;

public class UnexpectedErrorException extends RuntimeException {
    public UnexpectedErrorException(String message) {
        super(message);
    }
}
