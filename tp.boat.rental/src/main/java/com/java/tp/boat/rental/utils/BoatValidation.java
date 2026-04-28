package com.java.tp.boat.rental.utils;

import com.java.tp.boat.rental.model.BoatTypes;

public final class BoatValidation {
    
    public static String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Boat name cannot be blank");
        }
        return name.trim();
    }

    public static BoatTypes validateType(BoatTypes type) {
        if (type == null) {
            throw new IllegalArgumentException("Boat type cannot be null");
        }
        return type;
    } 

    public static Integer validateMaxCapacity(Integer maxCapacity) {
        if (maxCapacity == null || maxCapacity.toString().isBlank()) {
            throw new IllegalArgumentException("Boat max capacity cannot be blank");
        }
        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("Boat max capacity must be greater than 0");
        }
        return maxCapacity;
    }

    public static Float validateLength(Float length) {
        if (length == null || length.toString().isBlank()) {
            throw new IllegalArgumentException("Boat length cannot be blank");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Boat length must be greater than 0");
        }
        return length;
    }

    public static Float validateDailyRate(Float dailyRate) {
        if (dailyRate == null || dailyRate.toString().isBlank()) {
            throw new IllegalArgumentException("Boat daily rate cannot be blank");
        }
        if (dailyRate <= 0) {
            throw new IllegalArgumentException("Boat daily rate must be greater than 0");
        }
        return dailyRate;
    }

    public static Float validateDeposit(Float deposit) {
        if (deposit == null || deposit.toString().isBlank()) {
            throw new IllegalArgumentException("Boat deposit cannot be blank");
        }
        if (deposit < 0) {
            throw new IllegalArgumentException("Boat deposit cannot be negative");
        }
        return deposit;
    }

    public static Boolean validateNeedsLicense(Boolean needsLicense) {
        if (needsLicense == null) {
            throw new IllegalArgumentException("Boat license requirement cannot be null");
        }
        return needsLicense;
    }

}
