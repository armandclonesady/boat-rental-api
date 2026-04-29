package com.java.tp.boat.rental.model.request;

import com.java.tp.boat.rental.model.entity.BoatTypes;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BoatRequest {

    @NotBlank(message = "Name cannot be blank")
    @Max(message = "Name cannot be longer than 100 characters", value = 100)
    private String name;
    
    @NotBlank(message = "Type information is required")
    private BoatTypes type;

    @NotBlank(message = "Max capacity information is required")
    @Positive(message = "Max capacity must be a positive number")
    private Integer maxCapacity;

    @NotBlank(message = "Length information is required")
    @Positive(message = "Length must be a positive number")
    private Float length;

    @NotBlank(message = "Daily rate information is required")
    @Positive(message = "Daily rate must be a positive number")
    private Float dailyRate;

    @NotBlank(message = "Deposit information is required")
    @Positive(message = "Deposit must be a positive number")
    private Float deposit;  

    private Boolean needsLicense = false;
}
