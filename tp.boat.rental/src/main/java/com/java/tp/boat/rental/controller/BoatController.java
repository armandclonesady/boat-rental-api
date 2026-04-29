package com.java.tp.boat.rental.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.tp.boat.rental.exceptions.BoatDoesNotExistException;
import com.java.tp.boat.rental.exceptions.InvalidBoatException;
import com.java.tp.boat.rental.model.request.BoatCreationRequest;
import com.java.tp.boat.rental.model.request.BoatUpdateRequest;
import com.java.tp.boat.rental.model.response.BoatResponse;
import com.java.tp.boat.rental.service.BoatService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/boats")
public class BoatController {

    private BoatService boatService;

    @GetMapping("/")
    public Iterable<BoatResponse> getAllBots() {
        return boatService.getAllBoats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoatResponse> getBoatById(@PathVariable Long id) throws BoatDoesNotExistException {
        return ResponseEntity.ok(boatService.getBoatById(id));
    }

    @PostMapping("/")
    public ResponseEntity<BoatResponse> postBoat(@RequestBody @Valid BoatCreationRequest boatRequest) throws InvalidBoatException {
        return ResponseEntity.status(HttpStatus.CREATED).body(boatService.createBoat(boatRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BoatResponse> deleteBoat(@PathVariable Long id) throws BoatDoesNotExistException {
        return ResponseEntity.ok(boatService.deleteBoatById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoatResponse> putBoat(@PathVariable Long id, @RequestBody @Valid BoatUpdateRequest boatUpdateRequest) throws InvalidBoatException {
        return ResponseEntity.ok(boatService.updateBoat(id, boatUpdateRequest));
    }
}
