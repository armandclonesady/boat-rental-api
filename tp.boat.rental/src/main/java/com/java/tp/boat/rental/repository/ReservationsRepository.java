package com.java.tp.boat.rental.repository;

import org.springframework.data.repository.CrudRepository;

import com.java.tp.boat.rental.model.entity.ReservationsEntity;

public interface ReservationsRepository extends CrudRepository<ReservationsEntity, Long>{ }
