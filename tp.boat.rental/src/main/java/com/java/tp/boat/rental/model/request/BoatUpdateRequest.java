package com.java.tp.boat.rental.model.request;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoatUpdateRequest {
    @Null
    private Long bid;

    @Size(message = "Name cannot be longer than 100 characters", max = 100)
    private String name;
    
    @Size(message = "Type cannot be longer than 20 characters", max = 20)
    private String type;

    @Positive(message = "Max capacity must be a positive number")
    private Integer maxCapacity;

    @Positive(message = "Length must be a positive number")
    private Float length;

    @Positive(message = "Daily rate must be a positive number")
    private Float dailyRate;

    @Positive(message = "Deposit must be a positive number")
    private Float deposit;  

    private Boolean needsLicense = false; 
}
