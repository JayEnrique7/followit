package com.backend.demo.exceptions.handling;

import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.exceptions.UnexpectedErrorException;
import com.backend.demo.exceptions.attributes.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.util.Calendar;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionHandlingController extends ResponseStatusExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFound(NotFoundException ex) {
        return new ResponseEntity<>(
                exceptionResponse(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.name(),
                        ex.getMessage()),
                        HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> unauthorized(UnauthorizedException ex) {
        return new ResponseEntity<>(
                exceptionResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.name(),
                        ex.getMessage()),
                        HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UnexpectedErrorException.class)
    public ResponseEntity<ExceptionResponse> unexpectedError(UnexpectedErrorException ex) {
        return new ResponseEntity<>(
                exceptionResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.name(),
                        ex.getMessage()),
                        HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ExceptionResponse exceptionResponse(Integer status, String error, String message) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(Calendar.getInstance().getTime());
        response.setStatus(status);
        response.setError(error);
        response.setMessage(message);
        return response;
    }
}
