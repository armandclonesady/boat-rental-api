package com.java.tp.boat.rental.repository;

import org.springframework.data.repository.CrudRepository;

import com.java.tp.boat.rental.model.Reservations;

public interface ReservationsRepository extends CrudRepository<Reservations, Long>{ }
