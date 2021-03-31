package com.backend.demo.exceptions.attributes;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String errorCode;
    private String errorMessage;
}
