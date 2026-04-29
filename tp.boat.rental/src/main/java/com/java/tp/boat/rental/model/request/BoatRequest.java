package com.java.tp.boat.rental.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoatRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(message = "Name cannot be longer than 100 characters", max = 100)
    private String name;
    
    @NotBlank(message = "Type information is required")
    @Size(message = "Type cannot be longer than 20 characters", max = 20)
    private String type;

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
