package com.java.tp.boat.rental.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoatResponse {
    private Long bid;
    private String name;
    private String type;
    private Integer maxCapacity;
    private Float length;
    private Double dailyRate;
    private Double deposit;  
    private Boolean needsLicense;
}
