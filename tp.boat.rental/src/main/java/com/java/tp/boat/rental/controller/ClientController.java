package com.java.tp.boat.rental.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.tp.boat.rental.exceptions.InvalidClientException;
import com.java.tp.boat.rental.model.Client;
import com.java.tp.boat.rental.service.ClientService;

import lombok.Data;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
@Data
@RestController
@RequestMapping("/clients")
public class ClientController {
    
    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public Iterable<Client> getAllClient() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        if (id < 0) {
            return ResponseEntity.badRequest().body("ID must be positive");
        }
        Optional<Client> client = clientService.getClientById(id);
        if (client.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No client associated with id %d", id));
        } else {
            return ResponseEntity.ok(client);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> postClient(@RequestBody Client client) {
        try {;
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(client));
        } catch (InvalidClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        Client deletedClient = clientService.deleteClientById(id);
        if (deletedClient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("No client associated with id %d", id));
        } else {
            return ResponseEntity.ok(deletedClient);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putClient(@PathVariable Long id, @RequestBody Client client) throws InvalidClientException {
        try {
            return ResponseEntity.ok(clientService.updateClient(id, client));
        } catch (InvalidClientException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}
