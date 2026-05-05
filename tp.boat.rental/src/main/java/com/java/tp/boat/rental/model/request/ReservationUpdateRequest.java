package com.java.tp.boat.rental.model.request;

import java.sql.Date;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationUpdateRequest {
    @Null
    private Long rid;

    private Long cid;

    private Long bid;

    @Positive(message = "Amount of people must be a positive number")
    private Integer amountOfPeople;
    
    @Future(message = "Start time must be in the future")
    private Date startTime;
    
    @Future(message = "End time must be in the future")
    private Date endTime;
}
