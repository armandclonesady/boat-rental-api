package com.java.tp.boat.rental.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {
    
    @ExceptionHandler(InvalidClientException.class)
    public ResponseEntity<String> handleClientException(InvalidClientException e) {
        return ResponseEntity.badRequest().body("InvalidClientException from ClientController: " + e.getMessage());
    }

    @ExceptionHandler(InvalidBoatException.class)
    public ResponseEntity<String> handleBoatException(InvalidBoatException e) {
        return ResponseEntity.badRequest().body("InvalidBoatException from BoatController: " + e.getMessage());
    }

    @ExceptionHandler(ClientDoesNotExistException.class)
    public ResponseEntity<String> handleClientDoesNotExistException(ClientDoesNotExistException e) {
        return ResponseEntity.status(404).body("ClientDoesNotExistException from ClientController: " + e.getMessage());
    }

    @ExceptionHandler(BoatDoesNotExistException.class)
    public ResponseEntity<String> handleBoatDoesNotExistException(BoatDoesNotExistException e) {
        return ResponseEntity.status(404).body("BoatDoesNotExistException from BoatController: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
