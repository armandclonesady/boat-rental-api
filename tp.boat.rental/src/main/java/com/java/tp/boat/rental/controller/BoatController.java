package com.java.tp.boat.rental.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.model.entity.ClientEntity;
import com.java.tp.boat.rental.service.BoatService;

import lombok.Data;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Data
@Controller
@RestController
@RequestMapping("/boats")
public class BoatController {

    @Autowired
    private BoatService boatService;

    @GetMapping("/")
    public Iterable<BoatEntity> getAllBots() {
        return boatService.getAllBoats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoatById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().body("ID must be positive");
        }
        Optional<BoatEntity> boat = boatService.getBoatById(id);
        if (boat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No boat associated with id %d", id));
        } else {
            return ResponseEntity.ok(boat);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> postBoat(@RequestBody BoatEntity boat) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(boatService.createBoat(boat));
        } catch (InvalidBoatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoat(@PathVariable Long id) {
        BoatEntity deletedBoat = boatService.deleteBoatById(id);
        if (deletedBoat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No boat associated with id %d", id));
        } else {
            return ResponseEntity.ok(deletedBoat);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putBoat(@PathVariable Long id, @RequestBody BoatEntity boat) {
        try {
            return ResponseEntity.ok(boatService.updateBoat(id, boat));
        } catch (InvalidBoatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
