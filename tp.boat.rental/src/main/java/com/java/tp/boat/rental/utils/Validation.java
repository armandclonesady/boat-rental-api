package com.java.tp.boat.rental.utils;

import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.model.entity.BoatEntity;

public final class Validation {

    /**
     * Validates a boat and returns the validated boat.
     * @param boat the boat to be validated
     * @return the validated boat
     * @throws InvalidBoatException if the boat is invalid
     */
    public static BoatEntity validate(BoatEntity boat) throws InvalidBoatException {
        boat.setName(BoatValidation.validateName(boat.getName()));
        boat.setType(BoatValidation.validateType(boat.getType()));
        boat.setMaxCapacity(BoatValidation.validateMaxCapacity(boat.getMaxCapacity()));
        boat.setLength(BoatValidation.validateLength(boat.getLength()));
        boat.setDailyRate(BoatValidation.validateDailyRate(boat.getDailyRate()));
        boat.setDeposit(BoatValidation.validateDeposit(boat.getDeposit()));
        boat.setNeedsLicense(BoatValidation.validateNeedsLicense(boat.getNeedsLicense()));
        return boat;
    }

    public static BoatEntity updateBoat(BoatEntity existingBoat, BoatEntity updatedBoat) {
        if (updatedBoat.getName() != null) {
            existingBoat.setName(updatedBoat.getName());
        }
        if (updatedBoat.getType() != null) {
            existingBoat.setType(updatedBoat.getType());
        }
        if (updatedBoat.getMaxCapacity() != null) {
            existingBoat.setMaxCapacity(updatedBoat.getMaxCapacity());
        }
        if (updatedBoat.getLength() != null) {
            existingBoat.setLength(updatedBoat.getLength());
        }
        if (updatedBoat.getDailyRate() != null) {
            existingBoat.setDailyRate(updatedBoat.getDailyRate());
        }
        if (updatedBoat.getDeposit() != null) {
            existingBoat.setDeposit(updatedBoat.getDeposit());
        }
        if (updatedBoat.getNeedsLicense() != null) {
            existingBoat.setNeedsLicense(updatedBoat.getNeedsLicense());
        }
        return existingBoat;
    }
}
