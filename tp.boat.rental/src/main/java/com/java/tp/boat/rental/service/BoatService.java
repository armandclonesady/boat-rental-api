package com.java.tp.boat.rental.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.boat.BoatDoesNotExistException;
import com.java.tp.boat.rental.exceptions.boat.InvalidBoatException;
import com.java.tp.boat.rental.model.business.Boat;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.model.request.BoatCreationRequest;
import com.java.tp.boat.rental.model.request.BoatUpdateRequest;
import com.java.tp.boat.rental.repository.BoatRepository;
import com.java.tp.boat.rental.utils.mappers.BoatMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class BoatService {

    private BoatRepository boatRepository;
    private BoatMapper boatMapper;

    public Boat getBoatById(Long id) throws BoatDoesNotExistException{
        return boatMapper.toDomainFromEntity(boatRepository.findById(id).orElseThrow(() -> new BoatDoesNotExistException(String.format("No boat associated with id %d", id))));
    }

    public ArrayList<Boat> getAllBoats() {
        ArrayList<Boat> boats = new ArrayList<>();
        for (BoatEntity boatEntity : boatRepository.findAll()) {
            boats.add(boatMapper.toDomainFromEntity(boatEntity));
        }
        return boats;
    }

    public Boat createBoat(BoatCreationRequest boatRequest) throws InvalidBoatException {
        Boat boat = boatMapper.toDomainFromRequestCreation(boatRequest);
        boatRepository.save(boatMapper.toEntityFromDomain(boat));
        return boat;
    }

    public Boat deleteBoatById(Long id) throws BoatDoesNotExistException {
        Optional<BoatEntity> boatToBeDeleted = boatRepository.findById(id);
        if (boatToBeDeleted.isPresent()) {
            boatRepository.delete(boatToBeDeleted.get());
        }
        return boatToBeDeleted.map(boatMapper::toDomainFromEntity).orElseThrow(() -> new BoatDoesNotExistException(String.format("No boat associated with id %d", id)));
    }

    public Boat editBoat(BoatCreationRequest existingBoatRequest, BoatCreationRequest newBoatRequest) throws InvalidBoatException {
        Boat existingBoat = boatMapper.toDomainFromRequestCreation(existingBoatRequest);
        existingBoat.updateWith(boatMapper.toDomainFromRequestCreation(newBoatRequest));
        boatRepository.save(boatMapper.toEntityFromDomain(existingBoat));
        return existingBoat;
    }

    public Boat updateBoat(Long id, BoatUpdateRequest boatUpdateRequest) throws InvalidBoatException {
        Optional<BoatEntity> existingBoat = boatRepository.findById(id);
        Boat existingBoatDomain = boatMapper.toDomainFromEntity(existingBoat.get());
        BoatCreationRequest boatRequest = boatMapper.toRequestCreationFromRequestUpdate(boatUpdateRequest);
        Boat boat = boatMapper.toDomainFromRequestCreation(boatRequest);
        existingBoatDomain.updateWith(boat);
        boatRepository.save(boatMapper.toEntityFromDomain(existingBoatDomain));
        return existingBoatDomain;
    } 
}
