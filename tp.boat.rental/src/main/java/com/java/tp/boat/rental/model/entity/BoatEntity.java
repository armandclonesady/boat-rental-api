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

    public static float DEFAULT_DAILY_RATE = 100.0f;
    public static float DEFAULT_DEPOSIT = 200.0f;

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
    
    private Float dailyRate;

    private Float deposit;

    private Boolean needsLicense;
}
