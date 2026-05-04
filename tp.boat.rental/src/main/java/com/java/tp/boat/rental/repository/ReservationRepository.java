package com.java.tp.boat.rental.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long>{ 

    List<ReservationEntity> findByCid(Long cid);

    List<Reservation> findByBid(Long bid);

    List<ReservationEntity> findByCidAndBid(Long cid, Long bid);
}
