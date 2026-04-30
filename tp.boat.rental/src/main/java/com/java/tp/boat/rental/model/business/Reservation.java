package com.java.tp.boat.rental.model.business;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationStartIsAfterEndException;
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

    public Reservation(Long rid, Client client, Boat boat, Integer amountOfPeople, Date startTime, Date endTime) throws ClientHasNoLicenseException, ReservationForTooManyPeopleException, ReservationStartIsAfterEndException {
        // -- RG2
        this.checkDates(startTime, endTime); 
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

    private void checkDates(Date startTime, Date endTime) throws ReservationStartIsAfterEndException {
        if (startTime.after(endTime)) {
            throw new ReservationStartIsAfterEndException(String.format("Start time %s must be before end time %s", startTime.toString(), endTime.toString()));
        }
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

    // -- RG2
    private ReservationStatus calculateReservationStatus(Date startTime, Date endTime) {
        LocalDate now = LocalDate.now();
        LocalDate startLocalDate = startTime.toLocalDate();
        LocalDate endLocalDate = endTime.toLocalDate();
        if (startLocalDate.isAfter(now)) {
            return ReservationStatus.UPCOMING;
        } else if (endLocalDate.isBefore(now)) {
            return ReservationStatus.OVER;
        } else {
            return ReservationStatus.ONGOING;
        }
    }

    // -- RG4
    private void checkLicense(Client client, Boat boat) throws ClientHasNoLicenseException {
        if ((client.getHasLicense() == null || client.getHasLicense() == false) && boat.getNeedsLicense() == true) {
            throw new ClientHasNoLicenseException(String.format("Client with id %d does not have a license, but the boat with id %d requires one", client.getCid(), boat.getBid()));
        }
    }

    // -- RG5
    private void checkCapacity(Integer amountOfPeople, Boat boat) throws ReservationForTooManyPeopleException {
        if (amountOfPeople > boat.getMaxCapacity()) {
            throw new ReservationForTooManyPeopleException(String.format("Amount of people %d exceeds the maximum capacity of the boat with id %d, which is %d", amountOfPeople, boat.getBid(), boat.getMaxCapacity()));
        }
    }

    // -- RG6
    private Double calculatePrice(Boat boat, Date startTime, Date endTime) {
        Long nbDays = ChronoUnit.DAYS.between(startTime.toLocalDate(), endTime.toLocalDate());
        return nbDays.doubleValue() * boat.getDailyRate();
    }

    // -- RG7
    private Double calculateDeposit(Boat boat) {
        return boat.getDeposit();
    }

    public void updateWith(Reservation domainFromRequestCreation) {
        this.client = domainFromRequestCreation.getClient() != null ? domainFromRequestCreation.getClient() : this.client;
        this.boat = domainFromRequestCreation.getBoat() != null ? domainFromRequestCreation.getBoat() : this.boat;
        this.amountOfPeople = domainFromRequestCreation.getAmountOfPeople() != null ? domainFromRequestCreation.getAmountOfPeople() : this.amountOfPeople;
        this.startTime = domainFromRequestCreation.getStartTime() != null ? domainFromRequestCreation.getStartTime() : this.startTime;
        this.endTime = domainFromRequestCreation.getEndTime() != null ? domainFromRequestCreation.getEndTime() : this.endTime;
        if (!domainFromRequestCreation.getReservationStatus().equals(ReservationStatus.CANCELED)) {
            this.reservationStatus = calculateReservationStatus(this.startTime, this.endTime);
        }
        this.price = calculatePrice(this.boat, this.startTime, this.endTime);
    }
}
