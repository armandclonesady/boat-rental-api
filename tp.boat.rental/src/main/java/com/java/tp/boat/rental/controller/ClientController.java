package com.java.tp.boat.rental.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.tp.boat.rental.exceptions.ClientDoesNotExistException;
import com.java.tp.boat.rental.exceptions.InvalidClientException;
import com.java.tp.boat.rental.model.request.ClientRequest;
import com.java.tp.boat.rental.model.response.ClientResponse;
import com.java.tp.boat.rental.service.ClientService;

import jakarta.validation.Valid;
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
    public Iterable<ClientResponse> getAllClient() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable Long id) throws ClientDoesNotExistException {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PostMapping("/")
    public ResponseEntity<ClientResponse> postClient(@RequestBody @Valid ClientRequest clientRequest) throws InvalidClientException {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientResponse> deleteClient(@PathVariable Long id) throws ClientDoesNotExistException {
        return ResponseEntity.ok(clientService.deleteClientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> putClient(@PathVariable Long id, @RequestBody ClientRequest client) throws InvalidClientException {
        return ResponseEntity.ok(clientService.updateClient(id, client));
    }
    
}
