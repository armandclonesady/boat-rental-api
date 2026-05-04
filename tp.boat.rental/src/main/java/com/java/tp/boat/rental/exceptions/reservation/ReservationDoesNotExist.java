package com.java.tp.boat.rental.exceptions.reservation;

public class ReservationDoesNotExist extends RuntimeException {
    public ReservationDoesNotExist(String message) {
        super(message);
    }
    
}
