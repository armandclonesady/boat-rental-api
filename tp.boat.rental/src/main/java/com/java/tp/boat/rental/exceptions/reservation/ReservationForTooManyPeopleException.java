package com.java.tp.boat.rental.exceptions.reservation;

public class ReservationForTooManyPeopleException extends RuntimeException {
    public ReservationForTooManyPeopleException(String message) {
        super(message);
    }
}
