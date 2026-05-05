package com.java.tp.boat.rental.model.request;

import java.sql.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ReservationCreationRequest {
    @Null
    private Long rid;
    
    @NotNull(message = "Client ID information is required")
    private Long cid;

    @NotNull(message = "Boat ID information is required")
    private Long bid;

    @NotNull(message = "Amount of people information is required")
    @Positive(message = "Amount of people must be a positive number")
    private Integer amountOfPeople;
    
    // -- RG3
    @NotNull(message = "Start time information is required")
    @Future(message = "Start time must be in the future")
    private Date startTime;
    
    @NotNull(message = "End time information is required")
    @Future(message = "End time must be in the future")
    private Date endTime;
}
