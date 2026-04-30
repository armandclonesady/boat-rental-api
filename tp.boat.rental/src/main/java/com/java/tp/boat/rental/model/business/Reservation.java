package com.java.tp.boat.rental.model.business;

import java.sql.Date;

import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.model.entity.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long rid;
    private Client client;
    private Boat boat;
    private ReservationStatus reservationStatus;
    private Integer amountOfPeople;
    private Double price;
    private Double deposit;
    private Date startTime;
    private Date endTime;

    public Reservation(Long rid, Client client, Boat boat, Integer amountOfPeople, Date startTime, Date endTime) throws ClientHasNoLicenseException, ReservationForTooManyPeopleException {
        // -- RG4
        this.checkLicense(client, boat);
        // -- RG5
        this.checkCapacity(amountOfPeople, boat);


        // -- RG6
        this.price = this.calculatePrice(boat, startTime, endTime);
        // -- RG7
        this.deposit = this.calculateDeposit(boat);

        this.reservationStatus = this.calculateReservationStatus(startTime, endTime);
        
        this.rid = rid;
        this.client = client;
        this.boat = boat;
        this.amountOfPeople = amountOfPeople;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Reservation fromEntity(Long rid, Client client, Boat boat, Integer amountOfPeople, Double price, Double deposit, ReservationStatus status, Date startTime, Date endTime) {
        Reservation r = new Reservation();
        r.rid = rid;
        r.client = client;
        r.boat = boat;
        r.amountOfPeople = amountOfPeople;
        r.price = price;
        r.deposit = deposit;
        r.reservationStatus = status;
        r.startTime = startTime;
        r.endTime = endTime;
        return r;
    }

    private ReservationStatus calculateReservationStatus(Date startTime2, Date endTime2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateReservationStatus'");
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

    private Double calculatePrice(Boat boat, Date startTime2, Date endTime2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculatePrice'");
    }

    private Double calculateDeposit(Boat boat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateDeposit'");
    }
}
