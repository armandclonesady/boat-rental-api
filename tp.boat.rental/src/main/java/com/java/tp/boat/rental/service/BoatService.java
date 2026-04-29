package com.java.tp.boat.rental.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.repository.BoatRepository;
import com.java.tp.boat.rental.utils.Validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class BoatService {

    private BoatRepository boatRepository;

    public Optional<BoatEntity> getBoatById(Long id) {
        return boatRepository.findById(id);
    }

    public Iterable<BoatEntity> getAllBoats() {
        return boatRepository.findAll();
    }

    public BoatEntity createBoat(BoatEntity boat) throws InvalidBoatException {
        BoatEntity validatedBoat = Validation.validate(boat);
        return boatRepository.save(validatedBoat);
    }

    public void deleteBoat(BoatEntity boat) {
        boatRepository.delete(boat);
    }

    public BoatEntity deleteBoatById(Long id) {
        Optional<BoatEntity> boatToBeDeleted = boatRepository.findById(id);
        if (boatToBeDeleted.isPresent()) {
            boatRepository.delete(boatToBeDeleted.get());
        }
        return boatToBeDeleted.get();
    }

    public BoatEntity editBoat(BoatEntity existingBoat, BoatEntity newBoat) throws InvalidBoatException {
        return boatRepository.save(Validation.updateBoat(existingBoat, Validation.validate(newBoat)));
    }

    public BoatEntity updateBoat(Long id, BoatEntity boat) throws InvalidBoatException {
        Optional<BoatEntity> existingBoat = boatRepository.findById(id);
        if (existingBoat.isEmpty()) {
            return createBoat(boat);
        } else {
            return editBoat(existingBoat.get(), boat);
        }
    } 
}
