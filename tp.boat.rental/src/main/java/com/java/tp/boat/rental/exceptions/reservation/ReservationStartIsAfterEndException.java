package com.java.tp.boat.rental.exceptions.reservation;

public class ReservationStartIsAfterEndException extends Exception{
    public ReservationStartIsAfterEndException(String message) {
        super(message);
    }
}
