package com.java.tp.boat.rental.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.tp.boat.rental.model.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long>{ 

    

}
