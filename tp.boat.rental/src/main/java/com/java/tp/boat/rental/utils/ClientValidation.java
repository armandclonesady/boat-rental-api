package com.java.tp.boat.rental.utils;

import com.java.tp.boat.rental.exceptions.InvalidClientException;

public final class ClientValidation {
    public static String validateFirstName(String firstName) throws InvalidClientException {
        if (firstName == null || firstName.isBlank()) {
            throw new InvalidClientException("First name cannot be blank");
        } else {
            return firstName.trim()
                            .substring(0, 1)
                            .toUpperCase() + firstName.substring(1).toLowerCase();
        }
    }

    public static String validateLastName(String lastName) throws InvalidClientException {
        if (lastName == null || lastName.isBlank()) {
            throw new InvalidClientException("Last name cannot be blank");
        } else {
            return lastName.trim().toUpperCase();
        }
    }

    public static String validateEmail(String email) throws InvalidClientException {
        if (email == null || email.isBlank()) {
            throw new InvalidClientException("Email cannot be blank");
        }
        String validatedEmail = email.trim().toLowerCase();
        if (!validatedEmail.matches("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidClientException("Email is not valid");
        }
        return validatedEmail;
    }

    public static String validatePhoneNumber(String phoneNumber) throws InvalidClientException {
        if (phoneNumber == null) {
            return null;
        }
        if (phoneNumber.isBlank()) {
            throw new InvalidClientException("Phone number cannot be blank");
        }
        return phoneNumber;
    }

    public static Boolean validateHasLicense(Boolean hasLicense) throws InvalidClientException {
        if (hasLicense == null) {
            return false;
        }
        return hasLicense;
    }
}
