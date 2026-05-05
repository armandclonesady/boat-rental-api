package com.java.tp.boat.rental.service;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.reservation.BoatAlreadyReservedForDateException;
import com.java.tp.boat.rental.exceptions.reservation.ReservationDoesNotExist;
import com.java.tp.boat.rental.model.business.Boat;
import com.java.tp.boat.rental.model.business.Client;
import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.model.entity.ClientEntity;
import com.java.tp.boat.rental.model.entity.ReservationEntity;
import com.java.tp.boat.rental.model.entity.ReservationStatus;
import com.java.tp.boat.rental.model.request.ReservationCreationRequest;
import com.java.tp.boat.rental.model.request.ReservationUpdateRequest;
import com.java.tp.boat.rental.repository.ReservationRepository;
import com.java.tp.boat.rental.utils.mappers.BoatMapper;
import com.java.tp.boat.rental.utils.mappers.ClientMapper;
import com.java.tp.boat.rental.utils.mappers.ReservationMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class ReservationService {
    
    private ReservationRepository reservationsRepository;

    private ClientService clientService;
    private BoatService boatService;

    private ClientMapper clientMapper;
    private BoatMapper boatMapper;
    private ReservationMapper reservationMapper;

    public Reservation getReservationById(Long rid) {
        ReservationEntity reservationEntity = reservationsRepository.findById(rid).orElseThrow(() -> new ReservationDoesNotExist(String.format("No reservation associated with id %d", rid)));
        Client client = clientService.getClientById(reservationEntity.getCid().getCid());
        Boat boat = boatService.getBoatById(reservationEntity.getBid().getBid());
        return reservationMapper.toDomainFromEntity(reservationEntity, client, boat);
    }
    
    public ArrayList<Reservation> getAllReservations() {
    ArrayList<Reservation> reservations = new ArrayList<>();
    for (ReservationEntity entity : reservationsRepository.findAll()) {
        Client client = clientMapper.toDomainFromEntity(entity.getCid());
        Boat boat = boatMapper.toDomainFromEntity(entity.getBid());
        reservations.add(reservationMapper.toDomainFromEntity(entity, client, boat));
    }
    return reservations;
}

    /**
     * - la requete est valide si elle arrive ici, donc check RG3
     * - on créé la reservation 
     *      ( donc check: 
     *          - RG2 / RG3 / RG4 / RG5 / RG6 / RG7
     *      )
     * - RG1 pas de reservation de ce bateau sur le même créeau 
     */
    public Reservation createReservation(ReservationCreationRequest reservationRequest) {
        Client client = clientService.getClientById(reservationRequest.getCid());
        Boat boat = boatService.getBoatById(reservationRequest.getBid());
        Reservation reservation = reservationMapper.toDomainFromRequestCreation(reservationRequest, client, boat);
        if (checkIsAvailable(reservation.getBoat().getBid(), reservation.getStartTime().toLocalDate(), reservation.getEndTime().toLocalDate())) {
            ClientEntity clientEntity = clientService.getClientEntityById(reservation.getClient().getCid());
            BoatEntity boatEntity = boatService.getBoatEntityById(reservation.getBoat().getBid());
            ReservationEntity savedReservation = reservationsRepository.save(reservationMapper.toEntityFromDomain(reservation, clientEntity, boatEntity));
            return reservationMapper.toDomainFromEntity(savedReservation, client, boat);
        } else {
            throw new BoatAlreadyReservedForDateException(String.format("Boat with id %d is already reserved for the given dates", reservation.getBoat().getBid()));
        }
    }

    public boolean checkIsAvailable(Long bid, LocalDate startTime, LocalDate endTime) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationsRepository.findByBidBid(bid)) {
            Client client = clientService.getClientById(reservationEntity.getCid().getCid());
            Boat boat = boatService.getBoatById(reservationEntity.getBid().getBid());
            reservations.add(reservationMapper.toDomainFromEntity(reservationEntity, client, boat));
        }
        for (Reservation reservation : reservations) {
            if (reservation.getStartTime().toLocalDate().isBefore(endTime) || reservation.getEndTime().toLocalDate().isAfter(startTime)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIsAvailable(Long bid, LocalDate startTime, LocalDate endTime, Long reservationId) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationsRepository.findByBidBid(bid)) {
            Client client = clientService.getClientById(reservationEntity.getCid().getCid());
            Boat boat = boatService.getBoatById(reservationEntity.getBid().getBid());
            reservations.add(reservationMapper.toDomainFromEntity(reservationEntity, client, boat));
        }
        for (Reservation reservation : reservations) {
            if (!reservation.getRid().equals(reservationId) && (reservation.getStartTime().toLocalDate().isBefore(endTime) || reservation.getEndTime().toLocalDate().isAfter(startTime))) {
                return false;
            }
        }
        return true;
    }

    public Reservation deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservationsRepository.deleteById(id);
        return reservation;
    }

    public Reservation cancelReservationById(Long id) {
        ReservationEntity reservationEntity = reservationsRepository.findById(id).orElseThrow(() -> new ReservationDoesNotExist("No reservation associated with id " + id));
        reservationEntity.setStatus(ReservationStatus.CANCELED);
        reservationsRepository.save(reservationEntity);

        Client client = clientService.getClientById(reservationEntity.getCid().getCid());
        Boat boat = boatService.getBoatById(reservationEntity.getBid().getBid());
        return reservationMapper.toDomainFromEntity(reservationEntity, client, boat);

    }

    public Reservation editReservation(ReservationCreationRequest existingReservationRequest, ReservationCreationRequest newReservationRequest) {
        Client client = clientService.getClientById(existingReservationRequest.getCid());
        Boat boat = boatService.getBoatById(existingReservationRequest.getBid());
        Reservation existingReservation = reservationMapper.toDomainFromRequestCreation(existingReservationRequest, client, boat);
        if (!checkIsAvailable(existingReservation.getBoat().getBid(), existingReservation.getStartTime().toLocalDate(), existingReservation.getEndTime().toLocalDate())) {
            throw new BoatAlreadyReservedForDateException(String.format("Boat with id %d is already reserved for the given dates", existingReservation.getBoat().getBid()));
        }
        Client newClient = clientService.getClientById(newReservationRequest.getCid());
        Boat newBoat = boatService.getBoatById(newReservationRequest.getBid());
        existingReservation.updateWith(reservationMapper.toDomainFromRequestCreation(newReservationRequest, newClient, newBoat));
        ClientEntity clientEntity = clientService.getClientEntityById(existingReservation.getClient().getCid());
        BoatEntity boatEntity = boatService.getBoatEntityById(existingReservation.getBoat().getBid());
        reservationsRepository.save(reservationMapper.toEntityFromDomain(existingReservation, clientEntity, boatEntity));
        return existingReservation;
    }

    public Reservation updateReservation(Long id, ReservationUpdateRequest reservationUpdateRequest) {
        ReservationEntity existingReservationEntity = reservationsRepository.findById(id).orElseThrow(() -> new ReservationDoesNotExist("No reservation associated with id " + id));
        Client client = clientService.getClientById(existingReservationEntity.getCid().getCid());
        Boat boat = boatService.getBoatById(existingReservationEntity.getBid().getBid());
        Reservation existingReservation = reservationMapper.toDomainFromEntity(existingReservationEntity, client, boat);
        
        Client newClient = client;
        Boat newBoat = boat;

        //pas sur de cette approche
        if (reservationUpdateRequest.getCid() != null) {
            newClient = clientService.getClientById(reservationUpdateRequest.getCid());
        }
        if (reservationUpdateRequest.getBid() != null) {
            newBoat = boatService.getBoatById(reservationUpdateRequest.getBid());
        }
        if (reservationUpdateRequest.getStartTime() != null) {
            existingReservation.setStartTime(reservationUpdateRequest.getStartTime());
        }
        if (reservationUpdateRequest.getEndTime() != null) {
            existingReservation.setEndTime(reservationUpdateRequest.getEndTime());
        }

        Reservation updateReservation = reservationMapper.toDomainFromRequestUpdate(reservationUpdateRequest, newClient, newBoat);

        if (!checkIsAvailable(newBoat.getBid(), existingReservation.getStartTime().toLocalDate(), existingReservation.getEndTime().toLocalDate(), existingReservation.getRid())) {
            throw new BoatAlreadyReservedForDateException(String.format("Boat with id %d is already reserved for the given dates", updateReservation.getBoat().getBid()));
        }

        existingReservation.updateWith(updateReservation);
        ClientEntity clientEntity = clientService.getClientEntityById(existingReservation.getClient().getCid());
        BoatEntity boatEntity = boatService.getBoatEntityById(existingReservation.getBoat().getBid());
        reservationsRepository.save(reservationMapper.toEntityFromDomain(existingReservation, clientEntity, boatEntity));

        return existingReservation;
    }

    public ArrayList<Reservation> getReservationByCidAndBid(Long cid, Long bid) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationsRepository.findByCidCidAndBidBid(cid, bid)) {
            Client client = clientService.getClientById(reservationEntity.getCid().getCid());
            Boat boat = boatService.getBoatById(reservationEntity.getBid().getBid());
            reservations.add(reservationMapper.toDomainFromEntity(reservationEntity, client, boat));
        }
        return reservations;
    }

    public ArrayList<Reservation> getReservationsByCid(Long cid) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationsRepository.findByCidCid(cid)) {
            Client client = clientService.getClientById(reservationEntity.getCid().getCid());
            Boat boat = boatService.getBoatById(reservationEntity.getBid().getBid());
            reservations.add(reservationMapper.toDomainFromEntity(reservationEntity, client, boat));
        }
         return reservations;
    }

    public ArrayList<Reservation> getReservationsByBid(Long bid) {
        ArrayList<Reservation> reservations = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationsRepository.findByBidBid(bid)) {
            Client client = clientService.getClientById(reservationEntity.getCid().getCid());
            Boat boat = boatService.getBoatById(reservationEntity.getBid().getBid());
            reservations.add(reservationMapper.toDomainFromEntity(reservationEntity, client, boat));
        }
        return reservations;
    }
}
