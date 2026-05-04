package com.java.tp.boat.rental.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.boat.BoatDoesNotExistException;
import com.java.tp.boat.rental.exceptions.client.ClientDoesNotExistException;
import com.java.tp.boat.rental.exceptions.reservation.BoatAlreadyReservedForDateException;
import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationStartIsAfterEndException;
import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.entity.ReservationStatus;
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
     * - la requete est valide si elle arrive ici, donc check RG3
     * - on créé la reservation 
     *      ( donc check: 
     *          - RG2 / RG3 / RG4 / RG5 / RG6 / RG7
     *      )
     * - RG1 pas de reservation de ce bateau sur le même créeau 
     */
    public Reservation createReservation(ReservationCreationRequest reservationRequest) {
        Reservation reservation = reservationMapper.toDomainFromRequestCreation(reservationRequest);
        ArrayList<Reservation> reservations = getAllReservations();
        for (Reservation resInstance : reservations) {
            if (!resInstance.getBoat().getBid().equals(reservation.getBoat().getBid())) {
                continue;
            }
            ReservationStatus resInstanceStatus = resInstance.getReservationStatus();
            if (!(resInstanceStatus.equals(ReservationStatus.UPCOMING) || resInstanceStatus.equals(ReservationStatus.ONGOING))) {
                continue;
            }
            //TODO: refaire cette condition
            if (resInstanceStatus.equals(ReservationStatus.ONGOING)) {
                throw new BoatAlreadyReservedForDateException(String.format("Boat with id %d is already reserved for the given dates", reservation.getBoat().getBid()));
            }
        }
        reservationsRepository.save(reservationMapper.toEntityFromDomain(reservation));
        return reservation;
    }

    public boolean checkIsAvailable(Long bid) {
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) reservationsRepository.findByBid(bid);
        for (Reservation reservation : reservations) {
            if (reservation.getReservationStatus().equals(ReservationStatus.ONGOING)) {
                return true;
            }
        }
        return false;
    }

    public Reservation deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservation.setReservationStatus(ReservationStatus.CANCELED);
        reservationsRepository.save(reservationMapper.toEntityFromDomain(reservation));
        return reservation;
    }

    public Reservation cancelReservationById(Long id) {
        Reservation reservationToBeDeleted = getReservationById(id);
        reservationToBeDeleted.setReservationStatus(ReservationStatus.CANCELED);
        reservationsRepository.save(reservationMapper.toEntityFromDomain(reservationToBeDeleted));
        return reservationToBeDeleted;
    }

    public Reservation editReservation(ReservationCreationRequest existingReservationRequest, ReservationCreationRequest newReservationRequest) throws ClientHasNoLicenseException, ReservationForTooManyPeopleException, ClientDoesNotExistException, BoatDoesNotExistException, ReservationStartIsAfterEndException, BoatAlreadyReservedForDateException {
        Reservation existingReservation = reservationMapper.toDomainFromRequestCreation(existingReservationRequest);
        existingReservation.updateWith(reservationMapper.toDomainFromRequestCreation(newReservationRequest));
        reservationsRepository.save(reservationMapper.toEntityFromDomain(existingReservation));
        return existingReservation;
    }

    public Reservation updateReservation(Long id, ReservationCreationRequest reservationUpdateRequest) throws ClientHasNoLicenseException, ReservationForTooManyPeopleException, ClientDoesNotExistException, BoatDoesNotExistException, ReservationStartIsAfterEndException, BoatAlreadyReservedForDateException {
        Reservation existingReservation = getReservationById(id);
        Reservation reservation = reservationMapper.toDomainFromRequestCreation(reservationUpdateRequest);
        existingReservation.updateWith(reservation);
        reservationsRepository.save(reservationMapper.toEntityFromDomain(existingReservation));
        return existingReservation;
    }
}
