package com.java.tp.boat.rental.model.entity;

import jakarta.persistence.GenerationType;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "reservations")
public class ReservationEntity implements EntityModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private ClientEntity cid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid")
    private BoatEntity bid;

    @Enumerated(EnumType.ORDINAL)
    private ReservationStatus status;

    private Integer amountOfPeople;

    private Double price;

    private Double deposit;

    private Date startTime;

    private Date endTime;
}
