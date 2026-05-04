package com.java.tp.boat.rental.exceptions.boat;

public class BoatDoesNotExistException extends RuntimeException {
    public BoatDoesNotExistException(String message) {
        super(message);
    }
}
