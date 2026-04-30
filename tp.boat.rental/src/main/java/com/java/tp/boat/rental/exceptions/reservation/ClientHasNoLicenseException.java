package com.java.tp.boat.rental.exceptions.reservation;

public class ClientHasNoLicenseException extends Exception {
    public ClientHasNoLicenseException(String message) {
        super(message);
    }
}