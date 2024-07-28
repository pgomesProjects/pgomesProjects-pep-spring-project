package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Allows the class to handle exception techniques across the entire application
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Creates a ResponseEntity based on the information given by the ClientException.
     * @param ex The ClientException thrown.
     * @return A ResponseEntity object with a message and a status code.
     */
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<?> handleClientException(ClientException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.valueOf(ex.getStatusCode()));
    }
}