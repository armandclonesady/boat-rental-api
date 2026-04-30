package com.java.tp.boat.rental.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.model.business.Reservation;
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
}
