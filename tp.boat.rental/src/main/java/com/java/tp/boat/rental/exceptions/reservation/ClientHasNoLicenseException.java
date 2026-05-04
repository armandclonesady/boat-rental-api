package com.java.tp.boat.rental.exceptions.reservation;

public class ClientHasNoLicenseException extends RuntimeException {
    public ClientHasNoLicenseException(String message) {
        super(message);
    }
}