package com.java.tp.boat.rental.model.request;

import java.sql.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservationRequest {
    @NotBlank(message = "Client ID information is required")
    private Long cid;

    @NotBlank(message = "Boat ID information is required")
    private Long bid;

    @NotBlank(message = "Amount of people information is required")
    @Positive(message = "Amount of people must be a positive number")
    private Integer amountOfPeople;
    
    @NotBlank(message = "Start time information is required")
    @Future(message = "Start time must be in the future")
    private Date startTime;
    
    @Future(message = "End time must be in the future")
    private Date endTime;
}
