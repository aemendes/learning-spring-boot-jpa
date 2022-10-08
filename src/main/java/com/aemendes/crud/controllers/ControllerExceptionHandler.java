package com.aemendes.crud.controllers;

import com.aemendes.crud.exception.ErrorMessage;
import com.aemendes.crud.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

    /**
     * Custom Exceptions
     *
     * @param ex      Exception
     * @param request WebRequest
     * @return ErrorMessage
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    /**
     * Global Exception Handling
     *
     * @param ex      Exception
     * @param request WebRequest
     * @return ErrorMessage
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
