package com.java.tp.boat.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.tp.boat.rental.exceptions.boat.BoatDoesNotExistException;
import com.java.tp.boat.rental.exceptions.client.ClientDoesNotExistException;
import com.java.tp.boat.rental.exceptions.reservation.BoatAlreadyReservedForDateException;
import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationStartIsAfterEndException;
import com.java.tp.boat.rental.model.request.ReservationCreationRequest;
import com.java.tp.boat.rental.model.response.ReservationResponse;
import com.java.tp.boat.rental.service.ReservationService;
import com.java.tp.boat.rental.utils.mappers.ReservationMapper;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@Data
@RestController
@AllArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {
    
    private ReservationService reservationService;
    private ReservationMapper reservationMapperMapper;

    @GetMapping("/")
    public Iterable<ReservationResponse> getAllReservations() {
        return reservationService.getAllReservations().stream().map(reservationMapperMapper::toResponseFromDomain).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationMapperMapper.toResponseFromDomain(reservationService.getReservationById(id)));
    }

    @PostMapping("/")
    public ResponseEntity<ReservationResponse> postReservation(@RequestBody @Valid ReservationCreationRequest reservationCreationRequest) {
        return ResponseEntity.ok(reservationMapperMapper.toResponseFromDomain(reservationService.createReservation(reservationCreationRequest)));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> deleteReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationMapperMapper.toResponseFromDomain(reservationService.deleteReservation(id)));
    }
}
