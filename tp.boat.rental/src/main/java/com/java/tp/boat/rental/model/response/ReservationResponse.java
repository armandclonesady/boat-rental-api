package com.java.tp.boat.rental.model.response;

import java.sql.Date;

import com.java.tp.boat.rental.model.business.Boat;
import com.java.tp.boat.rental.model.business.Client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationResponse {
    private Long rid;
    private Client client;
    private Boat boat;
    private Integer amountOfPeople;
    private String reservationStatus;
    private Date startTime;
    private Date endTime;
}
