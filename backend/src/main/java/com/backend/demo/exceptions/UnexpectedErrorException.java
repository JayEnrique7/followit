package com.backend.demo.exceptions;

public class UnexpectedErrorException extends RuntimeException {
    public UnexpectedErrorException(String message) {
        super(message);
    }
}