package com.java.tp.boat.rental.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoatResponse {
    private String name;
    private String type;
    private Integer maxCapacity;
    private Float length;
    private Float dailyRate;
    private Float deposit;  
    private Boolean needsLicense;
}
