package com.java.tp.boat.rental.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.model.Boat;
import com.java.tp.boat.rental.repository.BoatRepository;
import com.java.tp.boat.rental.utils.Validation;

import lombok.Data;

@Service
@Data
public class BoatService {
    @Autowired
    private BoatRepository boatRepository;

    public Optional<Boat> getBoatById(Long id) {
        return boatRepository.findById(id);
    }

    public Iterable<Boat> getAllBoats() {
        return boatRepository.findAll();
    }

    public Boat createBoat(Boat boat) throws InvalidBoatException {
        Boat validatedBoat = Validation.validate(boat);
        return boatRepository.save(validatedBoat);
    }

    public void deleteBoat(Boat boat) {
        boatRepository.delete(boat);
    }

    public Boat deleteBoatById(Long id) {
        Optional<Boat> boatToBeDeleted = boatRepository.findById(id);
        if (boatToBeDeleted.isPresent()) {
            boatRepository.delete(boatToBeDeleted.get());
        }
        return boatToBeDeleted.get();
    }

    public Boat editBoat(Boat existingBoat, Boat newBoat) throws InvalidBoatException {
        return boatRepository.save(Validation.updateBoat(existingBoat, Validation.validate(newBoat)));
    }

    public Boat updateBoat(Long id, Boat boat) throws InvalidBoatException {
        Optional<Boat> existingBoat = boatRepository.findById(id);
        if (existingBoat.isEmpty()) {
            return createBoat(boat);
        } else {
            return editBoat(existingBoat.get(), boat);
        }
    } 
}
