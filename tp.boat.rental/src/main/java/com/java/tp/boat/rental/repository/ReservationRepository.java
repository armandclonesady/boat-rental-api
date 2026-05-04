package com.java.tp.boat.rental.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long>{ 

    List<ReservationEntity> findByCidCid(Long cid);

    List<Reservation> findByBidBid(Long bid);

    List<ReservationEntity> findByCidCidAndBidBid(Long cid, Long bid);
}
