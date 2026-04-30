package com.java.tp.boat.rental.utils.mappers;

import org.springframework.stereotype.Component;

import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.entity.ReservationEntity;
import com.java.tp.boat.rental.model.request.ReservationCreationRequest;
import com.java.tp.boat.rental.model.request.ReservationUpdateRequest;
import com.java.tp.boat.rental.model.response.ReservationResponse;

import lombok.Data;

@Component
@Data
public class ReservationMapper implements Mapper<Reservation, ReservationEntity, ReservationCreationRequest, ReservationUpdateRequest, ReservationResponse> {

    /*
     * Mapper interface for converting between different representations of a Reservation.
     * 
     * ReservationRequest -> Reservation (Domain Model) -> ReservationEntity (Database Entity) -> ReservationResponse (API Response)
     */

    private 

    @Override
    public Reservation toDomainFromRequestCreation(ReservationCreationRequest requestCreation) {
        Reservation reservation = new Reservation(
            requestCreation.getRid(),
            requestCreation.getClient(),
            requestCreation.getBoat(),
            requestCreation.getAmountOfPeople(),
            requestCreation.getStartTime(),
            requestCreation.getEndTime()
        );
        return reservation;
    }

    @Override
    public Reservation toDomainFromEntity(ReservationEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDomainFromEntity'");
    }

    @Override
    public ReservationEntity toEntityFromDomain(Reservation model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntityFromDomain'");
    }

    @Override
    public ReservationResponse toResponseFromDomain(Reservation model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toResponseFromDomain'");
    }

    @Override
    public ReservationResponse toResponseFromEntity(ReservationEntity entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toResponseFromEntity'");
    }

    @Override
    public ReservationCreationRequest toRequestCreationFromRequestUpdate(ReservationUpdateRequest requestUpdate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toRequestCreationFromRequestUpdate'");
    }
}
