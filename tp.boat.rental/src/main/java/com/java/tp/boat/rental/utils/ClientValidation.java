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

    public static Long validatePhoneNumber(Long phoneNumber) throws InvalidClientException {
        if (phoneNumber == null || phoneNumber.toString().isBlank()) {
            throw new InvalidClientException("Phone number cannot be blank");
        }
        if (!phoneNumber.toString().matches("^\\d{10}$")) {
            throw new InvalidClientException("Phone number must be 10 digits");
        }
        return phoneNumber;
    }

    public static Boolean validateHasLicense(Boolean hasLicense) throws InvalidClientException {
        if (hasLicense == null) {
            throw new InvalidClientException("Has license cannot be null");
        }
        return hasLicense;
    }
}
