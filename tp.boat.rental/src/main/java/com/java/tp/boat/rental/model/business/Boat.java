package com.java.tp.boat.rental.model.business;

import com.java.tp.boat.rental.model.entity.BoatTypes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Boat {
    public static float DEFAULT_DAILY_RATE = 100.0f;
    public static float DEFAULT_DEPOSIT = 200.0f;

    private Long bid;
    private String name;
    private BoatTypes type;
    private Integer maxCapacity;
    private Float length;
    private Float dailyRate;
    private Float deposit;
    private Boolean needsLicense;

    public void updateWith(Boat newBoat) {
        this.name = newBoat.getName() != null ? newBoat.getName() : this.name;
        this.type = newBoat.getType() != null ? newBoat.getType() : this.type;
        this.maxCapacity = newBoat.getMaxCapacity() != null ? newBoat.getMaxCapacity() : this.maxCapacity;
        this.length = newBoat.getLength() != null ? newBoat.getLength() : this.length;
        this.dailyRate = newBoat.getDailyRate() != null ? newBoat.getDailyRate() : this.dailyRate;
        this.deposit = newBoat.getDeposit() != null ? newBoat.getDeposit() : this.deposit;
        this.needsLicense = newBoat.getNeedsLicense() != null ? newBoat.getNeedsLicense() : this.needsLicense;
    }
}
