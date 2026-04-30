package com.java.tp.boat.rental.utils.mappers;

import com.java.tp.boat.rental.exceptions.boat.BoatDoesNotExistException;
import com.java.tp.boat.rental.exceptions.client.ClientDoesNotExistException;
import com.java.tp.boat.rental.exceptions.reservation.ClientHasNoLicenseException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationStartIsAfterEndException;

public interface Mapper<Model, Entity, RequestCreation, RequestUpdate, Response> {
    Model toDomainFromRequestCreation(RequestCreation requestCreation) throws ClientHasNoLicenseException, ReservationForTooManyPeopleException, ClientDoesNotExistException, BoatDoesNotExistException, ReservationStartIsAfterEndException;
    Model toDomainFromEntity(Entity entity);
    Entity toEntityFromDomain(Model model);
    Response toResponseFromDomain(Model model);
    Response toResponseFromEntity(Entity entity);
    RequestCreation toRequestCreationFromRequestUpdate(RequestUpdate requestUpdate);
    
    
}
