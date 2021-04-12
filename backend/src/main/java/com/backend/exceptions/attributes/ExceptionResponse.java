package com.backend.exceptions.attributes;

import lombok.Data;

import java.util.Date;

@Data
public class ExceptionResponse {
    private Date timestamp;
    private Integer status;
    private String error;
    private String message;
}
