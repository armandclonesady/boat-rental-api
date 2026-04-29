package com.java.tp.boat.rental.utils;

import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.exceptions.InvalidClientException;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.model.entity.ClientEntity;

public final class Validation {

    /**
     * Validates a client and returns the validated client.
        For this validation, we are assuming that we need to give a value to all the fields.
     * @param client the client to be validated
     * @return the validated client
     * @throws InvalidClientException if the client is invalid
     */
    public static ClientEntity validate(ClientEntity client) throws InvalidClientException {
        client.setFirstName(ClientValidation.validateFirstName(client.getFirstName()));
        client.setLastName(ClientValidation.validateLastName(client.getLastName()));
        client.setEmail(ClientValidation.validateEmail(client.getEmail()));
        client.setPhoneNumber(ClientValidation.validatePhoneNumber(client.getPhoneNumber()));
        client.setHasLicense(ClientValidation.validateHasLicense(client.getHasLicense()));
        return client;
    }

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

    /**
     * Updates an existing client with the values of an updated client. Only the fields that are not null in the updated client will be updated in the existing client.
     * @param existingClient client already in the database
     * @param updatedClient the new client with the updated values. Only the fields that are not null will be updated in the existing client.
     * @return the updated client
     */
    public static ClientEntity updateClient(ClientEntity existingClient, ClientEntity updatedClient) {
        if (updatedClient.getFirstName() != null) {
            existingClient.setFirstName(updatedClient.getFirstName());
        }
        if (updatedClient.getLastName() != null) {
            existingClient.setLastName(updatedClient.getLastName());
        }
        if (updatedClient.getEmail() != null) {
            existingClient.setEmail(updatedClient.getEmail());
        }
        if (updatedClient.getPhoneNumber() != null) {
            existingClient.setPhoneNumber(updatedClient.getPhoneNumber());
        }
        if (updatedClient.getHasLicense() != null) {
            existingClient.setHasLicense(updatedClient.getHasLicense());
        }

        return existingClient;
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
