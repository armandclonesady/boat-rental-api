package com.java.tp.boat.rental.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.boat.BoatDoesNotExistException;
import com.java.tp.boat.rental.exceptions.client.ClientDoesNotExistException;
import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.request.ReservationCreationRequest;
import com.java.tp.boat.rental.repository.ReservationRepository;
import com.java.tp.boat.rental.utils.mappers.ReservationMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class ReservationService {
    
    private ReservationRepository reservationsRepository;
    private ReservationMapper reservationMapper;

    public Reservation getReservationById(Long rid) {
        return reservationMapper.toDomainFromEntity(reservationsRepository.findById(rid).orElseThrow());
    }
    
    public ArrayList<Reservation> getAllReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservationsRepository.findAll().forEach(reservationEntity -> reservations.add(reservationMapper.toDomainFromEntity(reservationEntity)));
        return reservations;
    }

    /**
     * Il faut vérifier:
     * - client existe
     * - bateau existe
     * - on créé la reservation 
     *      ( donc check: 
     *          - RG2
     *          - RG3
     *          - RG4
     *          - RG5 
     *          - RG6 / RG7
     *      )
     * - RG1 pas de reservation de ce bateau sur le même créeau
     * 
     */
    public Reservation createReservation(ReservationCreationRequest reservationRequest) throws ClientHasNoLicenseException, ReservationForTooManyPeopleException, ClientDoesNotExistException, BoatDoesNotExistException {
        // RG2, RG3, RG4, RG5, RG6, RG7
        Reservation reservation = reservationMapper.toDomainFromRequestCreation(reservationRequest);
        ArrayList<Reservation> reservations = getAllReservations();
        for (Reservation resInstance : reservations) {
            Date resInstanceStart = resInstance.getStartTime();
            Date resInstanceEnd = resInstance.getEndTime();
            Date newResStart = reservation.getStartTime();
            Date newResEnd = reservation.getEndTime();
            // RG1
            if (newResStart.before(resInstanceEnd) || newResEnd.before(resInstanceEnd)) {
                throw new ReservationForTooManyPeopleException(String.format("The boat is already reserved for the time slot between %s and %s", resInstanceStart.toString(), resInstanceEnd.toString()));
            }
        }

        return reservation;
    }
}
