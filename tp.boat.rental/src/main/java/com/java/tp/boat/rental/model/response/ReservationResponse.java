package com.java.tp.boat.rental.model.response;

import java.sql.Date;

import com.java.tp.boat.rental.model.business.Boat;
import com.java.tp.boat.rental.model.business.Client;

import lombok.Data;

@Data
public class ReservationResponse {
    private Client client;
    private Boat boat;
    private Integer amountOfPeople;
    private Date startTime;
    private Date endTime;
}
