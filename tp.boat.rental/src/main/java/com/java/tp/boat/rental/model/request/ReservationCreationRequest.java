package com.java.tp.boat.rental.model.request;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservationCreationRequest {
    @Null
    private Long rid;
    
    @NotBlank(message = "Client ID information is required")
    private Long cid;

    @NotBlank(message = "Boat ID information is required")
    private Long bid;

    @NotBlank(message = "Amount of people information is required")
    @Positive(message = "Amount of people must be a positive number")
    private Integer amountOfPeople;
    
    // -- RG3
    @NotBlank(message = "Start time information is required")
    @Future(message = "Start time must be in the future")
    private Date startTime;
    
    @Future(message = "End time must be in the future")
    private Date endTime;
}
