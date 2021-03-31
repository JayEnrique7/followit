package com.backend.demo.exceptions;

public class Unauthorized extends RuntimeException {
    public Unauthorized(String message) {
        super(message);
    }
}
