package com.backend.demo.exceptions.controller;

import com.backend.demo.exceptions.NotFound;
import com.backend.demo.exceptions.Unauthorized;
import com.backend.demo.exceptions.UnexpectedError;
import com.backend.demo.exceptions.attributes.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlingController extends ResponseStatusExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<ExceptionResponse> notFound(NotFound ex) {
        return new ResponseEntity<>(
                exceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<ExceptionResponse> unauthorized(Unauthorized ex) {
        return new ResponseEntity<>(
                exceptionResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnexpectedError.class)
    public ResponseEntity<ExceptionResponse> unexpectedError(UnexpectedError ex) {
        return new ResponseEntity<>(
                exceptionResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionResponse exceptionResponse(String message, int status) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(Integer.toString(status));
        response.setErrorMessage(message);
        return response;
    }
}
