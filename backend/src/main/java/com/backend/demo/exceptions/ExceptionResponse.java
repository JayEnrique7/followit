package com.backend.demo.exceptions;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String errorCode;
    private String errorMessage;
    private String notAuthorized;
}