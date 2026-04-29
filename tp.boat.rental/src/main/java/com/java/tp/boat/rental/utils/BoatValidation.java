package com.java.tp.boat.rental.utils;

import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.model.entity.BoatTypes;

public final class BoatValidation {
    
    public static String validateName(String name) throws InvalidBoatException {
        if (name == null || name.isBlank()) {
            throw new InvalidBoatException("Boat name cannot be blank");
        }
        return name.trim();
    }

    public static BoatTypes validateType(BoatTypes type) throws InvalidBoatException {
        if (type == null) {
            throw new InvalidBoatException("Boat type cannot be null");
        }
        return type;
    } 

    public static Integer validateMaxCapacity(Integer maxCapacity) throws InvalidBoatException {
        if (maxCapacity == null || maxCapacity.toString().isBlank()) {
            throw new InvalidBoatException("Boat max capacity cannot be blank");
        }
        if (maxCapacity <= 0) {
            throw new InvalidBoatException("Boat max capacity must be greater than 0");
        }
        return maxCapacity;
    }

    public static Float validateLength(Float length) throws InvalidBoatException {
        if (length == null || length.toString().isBlank()) {
            throw new InvalidBoatException("Boat length cannot be blank");
        }
        if (length <= 0) {
            throw new InvalidBoatException("Boat length must be greater than 0");
        }
        return length;
    }

    public static Float validateDailyRate(Float dailyRate) throws InvalidBoatException {
        if (dailyRate == null || dailyRate.toString().isBlank()) {
            return BoatEntity.DEFAULT_DAILY_RATE;
        }
        if (dailyRate <= 0) {
            throw new InvalidBoatException("Boat daily rate must be greater than 0");
        }
        return dailyRate;
    }

    public static Float validateDeposit(Float deposit) throws InvalidBoatException{
        if (deposit == null || deposit.toString().isBlank()) {
            return BoatEntity.DEFAULT_DEPOSIT;
        }
        if (deposit < 0) {
            throw new InvalidBoatException("Boat deposit cannot be negative");
        }
        return deposit;
    }

    public static Boolean validateNeedsLicense(Boolean needsLicense) {
        if (needsLicense == null) {
            return true; // Default to true if not provided
        }
        return needsLicense;
    }

}
