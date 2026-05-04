package com.java.tp.boat.rental.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.reservation.BoatAlreadyReservedForDateException;
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
        if (checkIsAvailable(reservation.getBoat().getBid(), reservation.getStartTime().toLocalDate(), reservation.getEndTime().toLocalDate())) {
            reservationsRepository.save(reservationMapper.toEntityFromDomain(reservation));
        } else {
            throw new BoatAlreadyReservedForDateException(String.format("Boat with id %d is already reserved for the given dates", reservation.getBoat().getBid()));
        }
        return reservation;
    }

    public boolean checkIsAvailable(Long bid, LocalDate startTime, LocalDate endTime) {
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) reservationsRepository.findByBidBid(bid).stream().map(reservationMapper::toDomainFromEntity).collect(Collectors.toList());
        for (Reservation reservation : reservations) {
            if (reservation.getStartTime().toLocalDate().isBefore(endTime) || reservation.getEndTime().toLocalDate().isAfter(startTime)) {
                return false;
            }
        }
        return true;
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

    public Reservation editReservation(ReservationCreationRequest existingReservationRequest, ReservationCreationRequest newReservationRequest) {
        Reservation existingReservation = reservationMapper.toDomainFromRequestCreation(existingReservationRequest);
        if (!checkIsAvailable(existingReservation.getBoat().getBid(), existingReservation.getStartTime().toLocalDate(), existingReservation.getEndTime().toLocalDate())) {
            throw new BoatAlreadyReservedForDateException(String.format("Boat with id %d is already reserved for the given dates", existingReservation.getBoat().getBid()));
        }
        existingReservation.updateWith(reservationMapper.toDomainFromRequestCreation(newReservationRequest));
        reservationsRepository.save(reservationMapper.toEntityFromDomain(existingReservation));
        return existingReservation;
    }

    public Reservation updateReservation(Long id, ReservationCreationRequest reservationUpdateRequest) {
        Reservation existingReservation = getReservationById(id);
        if (!checkIsAvailable(existingReservation.getBoat().getBid(), existingReservation.getStartTime().toLocalDate(), existingReservation.getEndTime().toLocalDate())) {
            throw new BoatAlreadyReservedForDateException(String.format("Boat with id %d is already reserved for the given dates", existingReservation.getBoat().getBid()));
        }
        Reservation reservation = reservationMapper.toDomainFromRequestCreation(reservationUpdateRequest);
        existingReservation.updateWith(reservation);
        reservationsRepository.save(reservationMapper.toEntityFromDomain(existingReservation));
        return existingReservation;
    }

    public ArrayList<Reservation> getReservationByCidAndBid(Long cid, Long bid) {
        return (ArrayList<Reservation>) reservationsRepository.findByCidCidAndBidBid(cid, bid).stream().map(reservationMapper::toDomainFromEntity).collect(Collectors.toList());
    }

    public ArrayList<Reservation> getReservationsByCid(Long cid) {
        return (ArrayList<Reservation>) reservationsRepository.findByCidCid(cid).stream().map(reservationMapper::toDomainFromEntity).collect(Collectors.toList());
    }

    public ArrayList<Reservation> getReservationsByBid(Long bid) {
        return (ArrayList<Reservation>) reservationsRepository.findByBidBid(bid).stream().map(reservationMapper::toDomainFromEntity).collect(Collectors.toList());
    }
}
