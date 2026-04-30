package com.java.tp.boat.rental.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "boats")
public class BoatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    @Column(length = 100)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private BoatTypes type;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Float length;
    
    private Double dailyRate;

    private Double deposit;

    private Boolean needsLicense;
}
