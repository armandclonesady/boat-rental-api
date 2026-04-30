package com.java.tp.boat.rental.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoatCreationRequest {
    @Null
    private Long bid;

    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    @Size(message = "Name cannot be longer than 100 characters", max = 100)
    private String name;
    
    @NotBlank(message = "Type information is required")
    @NotNull(message = "Type cannot be null")
    @Size(message = "Type cannot be longer than 20 characters", max = 20)
    private String type;

    @NotNull(message = "Max capacity cannot be null")
    @Positive(message = "Max capacity must be a positive number")
    private Integer maxCapacity;

    @NotNull(message = "Length cannot be null")
    @Positive(message = "Length must be a positive number")
    private Float length;

    @Positive(message = "Daily rate must be a positive number")
    private Float dailyRate;

    @Positive(message = "Deposit must be a positive number")
    private Float deposit;  

    private Boolean needsLicense = false;
}
