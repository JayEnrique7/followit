package com.backend.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

public class UnauthorizedException extends HttpClientErrorException {
    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}