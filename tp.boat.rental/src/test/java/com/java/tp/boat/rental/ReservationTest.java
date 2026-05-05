package com.java.tp.boat.rental;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationStartIsAfterEndException;
import com.java.tp.boat.rental.model.business.Boat;
import com.java.tp.boat.rental.model.business.Client;
import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.entity.BoatTypes;
import com.java.tp.boat.rental.model.entity.ReservationStatus;

public class ReservationTest {

    public Boat boat1;
    public Client client1;
    public LocalDate startDate = LocalDate.now().plusMonths(1);
    public LocalDate endDate = startDate.plusDays(5);

    @BeforeEach
    public void setup() {
        boat1 = new Boat(1L, "Cavoboat", BoatTypes.MOTOR, 5, 420F, 150D, 20D, true);
        client1 = new Client(1L, "Simon", "Puech", "simon.puech@example.com", "0987654321", true);
    }

    @Test
    public void testCheckDatesWorks() {
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate));
        assert(reservation.getStartTime().equals(Date.valueOf(startDate)));
        assert(reservation.getEndTime().equals(Date.valueOf(endDate)));
    }

    @Test
    public void testCheckDatesThrowsWhenStartDateIsAfterEndDate() {
        assertThrows(ReservationStartIsAfterEndException.class, () ->
            new Reservation(1L, client1, boat1, 5, Date.valueOf(endDate), Date.valueOf(startDate))
        );
    }

    @Test
    public void testCheckLicenseWorks() {
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate));
        assert(reservation.getClient().getHasLicense().equals(true));
        assert(reservation.getBoat().getNeedsLicense().equals(true));
    }

    @Test
    public void testCheckLicenseThrowsClientHasNoLicenseException() {
        Client client2 = new Client(2L, "Atrioc", "Puech", "atrioc.puech@example.com", "0987654321", false);
        assertThrows(ClientHasNoLicenseException.class, () -> 
            new Reservation(1L, client2, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate))
        );
    }
    
    @Test
    public void testCheckCapacityWorks() {
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate));
        assert(reservation.getAmountOfPeople().equals(5));
        assert(reservation.getBoat().getMaxCapacity().equals(5));
    }

    @Test
    public void testCheckCapacityThrowsReservationForTooManyPeopleException() {
        Boat boat2 = new Boat(2L, "Knot Working", BoatTypes.SAILING, 1, 450F, 200D, 25D, false);
        assertThrows(ReservationForTooManyPeopleException.class, () -> 
            new Reservation(1L, client1, boat2, 5, Date.valueOf(startDate), Date.valueOf(endDate))
        );
    }

    @Test
    public void testCalculatePriceWorks() {
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate));
        Double expectedPrice = ChronoUnit.DAYS.between(startDate, endDate) * boat1.getDailyRate();
        assert(reservation.getPrice().equals(expectedPrice));
    }

    @Test
    public void testCalculateDepositWorks() {
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate));
        Double expectedDeposit = boat1.getDeposit();
        assert(reservation.getDeposit().equals(expectedDeposit));
    }

    @Test
    public void testCalculateReservationStatusUpcoming() {
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate));
        assert(reservation.getReservationStatus().equals(ReservationStatus.UPCOMING));
    }

    @Test
    public void testCalculateReservationStatusOngoing() {
        LocalDate now = LocalDate.now();
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(now.minusDays(1)), Date.valueOf(now.plusDays(1)));
        assert(reservation.getReservationStatus().equals(ReservationStatus.ONGOING));
    }

    @Test
    public void testCalculateReservationStatusOver() {
        LocalDate now = LocalDate.now();
        Reservation reservation = new Reservation(1L, client1, boat1, 5, Date.valueOf(now.minusDays(5)), Date.valueOf(now.minusDays(1)));
        assert(reservation.getReservationStatus().equals(ReservationStatus.OVER));
    }

    @Test
    public void testUpdateWithOverridesWhenValueIsPresent() {
        Reservation reservation1 = new Reservation(1L, client1, boat1, 5, Date.valueOf(startDate), Date.valueOf(endDate));
        Boat boat2 = new Boat(2L, "Knot Working", BoatTypes.SAILING, 6, 450F, 200D, 25D, false);
        Client client2 = new Client(2L, "Atrioc", "Puech", "atrioc.puech@example.com", "0987654321", false);
        Reservation reservation2 = new Reservation(2L, client2, boat2, 4, Date.valueOf(startDate.plusMonths(1)), Date.valueOf(endDate.plusMonths(1)));
        reservation1.updateWith(reservation2);
        assert(reservation1.getClient().getFirstName().equals("Atrioc"));
        assert(reservation1.getBoat().getName().equals("Knot Working"));
        assert(reservation1.getAmountOfPeople().equals(4));
        assert(reservation1.getStartTime().equals(Date.valueOf(startDate.plusMonths(1))));
        assert(reservation1.getEndTime().equals(Date.valueOf(endDate.plusMonths(1))));
    }
}
