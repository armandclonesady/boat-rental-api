package com.java.tp.boat.rental.exceptions.reservation;

public class BoatAlreadyReservedForDateException extends Exception {
    public BoatAlreadyReservedForDateException(String message) {
        super(message);
    }
}
