package com.java.tp.boat.rental.exceptions.reservation;

public class ReservationStartIsAfterEndException extends RuntimeException{
    public ReservationStartIsAfterEndException(String message) {
        super(message);
    }
}
