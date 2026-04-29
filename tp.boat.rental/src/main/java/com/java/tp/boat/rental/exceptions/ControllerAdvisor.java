package com.java.tp.boat.rental.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(InvalidClientException.class)
    public ResponseEntity<String> handleClientException(InvalidClientException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(InvalidBoatException.class)
    public ResponseEntity<String> handleBoatException(InvalidBoatException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ClientDoesNotExistException.class)
    public ResponseEntity<String> handleClientDoesNotExistException(ClientDoesNotExistException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(BoatDoesNotExistException.class)
    public ResponseEntity<String> handleBoatDoesNotExistException(BoatDoesNotExistException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
