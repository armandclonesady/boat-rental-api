package com.java.tp.boat.rental.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.BoatDoesNotExistException;
import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.model.business.Boat;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.model.request.BoatCreationRequest;
import com.java.tp.boat.rental.model.request.BoatUpdateRequest;
import com.java.tp.boat.rental.model.response.BoatResponse;
import com.java.tp.boat.rental.model.response.ClientResponse;
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

    public BoatResponse getBoatById(Long id) throws BoatDoesNotExistException{
        return boatMapper.toResponse(boatRepository.findById(id).orElseThrow(() -> new BoatDoesNotExistException(String.format("No boat associated with id %d", id))));
    }

    public ArrayList<BoatResponse> getAllBoats() {
        ArrayList<BoatResponse> boatResponses = new ArrayList<>();
        for (BoatEntity boatEntity : boatRepository.findAll()) {
            boatResponses.add(boatMapper.toResponse(boatEntity));
        }
        return boatResponses;
    }

    public BoatResponse createBoat(BoatCreationRequest boatRequest) throws InvalidBoatException {
        Boat boat = boatMapper.toDomain(boatRequest);
        return boatMapper.toResponse(boatRepository.save(boatMapper.toEntity(boat)));
    }

    public BoatResponse deleteBoatById(Long id) throws BoatDoesNotExistException {
        Optional<BoatEntity> boatToBeDeleted = boatRepository.findById(id);
        if (boatToBeDeleted.isPresent()) {
            boatRepository.delete(boatToBeDeleted.get());
        }
        return boatToBeDeleted.map(boatMapper::toResponse).orElseThrow(() -> new BoatDoesNotExistException(String.format("No boat associated with id %d", id)));
    }

    public BoatResponse editBoat(BoatCreationRequest existingBoatRequest, BoatCreationRequest newBoatRequest) throws InvalidBoatException {
        Boat existingBoat = boatMapper.toDomain(existingBoatRequest);
        existingBoat.updateWith(boatMapper.toDomain(newBoatRequest));
        return boatMapper.toResponse(existingBoat);
    }

    public BoatResponse updateBoat(Long id, BoatUpdateRequest boatUpdateRequest) throws InvalidBoatException {
        Optional<BoatEntity> existingBoat = boatRepository.findById(id);
        Boat existingBoatDomain = boatMapper.toDomain(existingBoat.get());
        BoatCreationRequest boatRequest = BoatMapper.toBoatRequest(boatUpdateRequest);
        Boat boat = boatMapper.toDomain(boatRequest);
        existingBoatDomain.updateWith(boat);
        return boatMapper.toResponse(existingBoatDomain);
    } 
}
