package com.java.tp.boat.rental.exceptions.reservation;

public class BoatAlreadyReservedForDateException extends RuntimeException {
    public BoatAlreadyReservedForDateException(String message) {
        super(message);
    }
}
