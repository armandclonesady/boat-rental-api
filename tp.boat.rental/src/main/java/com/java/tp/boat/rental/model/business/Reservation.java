package com.java.tp.boat.rental.model.business;

import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.model.entity.ReservationStatus;

import lombok.Data;

@Data
public class Reservation {
    private Long rid;
    private Client client;
    private Boat boat;
    private ReservationStatus reservationStatus;
    private Integer amountOfPeople;
    private Double price;
    private Double deposit;
    private String startTime;
    private String endTime;

    private Reservation() {}

    private Reservation(Long rid, Client client, Boat boat, ReservationStatus reservationStatus, Integer amountOfPeople, String startTime, String endTime) throws ClientHasNoLicenseException, ReservationForTooManyPeopleException {
        // -- RG4
        this.checkLicense(client, boat);
        // -- RG5
        this.checkCapacity(amountOfPeople, boat);
        // -- RG6
        this.calculatePrice(boat, startTime, endTime);

        // -- RG7
        this.calculateDeposit(boat);
        
        this.rid = rid;
        this.client = client;
        this.boat = boat;
        this.reservationStatus = reservationStatus;
        this.amountOfPeople = amountOfPeople;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private void checkLicense(Client client, Boat boat) throws ClientHasNoLicenseException {
        if ((client.getHasLicense() == null || client.getHasLicense() == false) && boat.getNeedsLicense() == true) {
            throw new ClientHasNoLicenseException(String.format("Client with id %d does not have a license, but the boat with id %d requires one", client.getCid(), boat.getBid()));
        }
    }

    private void checkCapacity(Integer amountOfPeople, Boat boat) throws ReservationForTooManyPeopleException {
        if (amountOfPeople > boat.getMaxCapacity()) {
            throw new ReservationForTooManyPeopleException(String.format("Amount of people %d exceeds the maximum capacity of the boat with id %d, which is %d", amountOfPeople, boat.getBid(), boat.getMaxCapacity()));
        }
    }

    private void calculatePrice(Boat boat, String startTime, String endTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculatePrice'");
    }

    private void calculateDeposit(Boat boat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateDeposit'");
    }
}
