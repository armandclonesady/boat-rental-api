package com.java.tp.boat.rental.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.InvalidClientException;
import com.java.tp.boat.rental.model.Client;
import com.java.tp.boat.rental.repository.ClientRepository;
import com.java.tp.boat.rental.utils.Validation;

import lombok.Data;

@Service
@Data
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client createClient(Client client) throws InvalidClientException {
        Client validatedClient = Validation.validate(client);
        return clientRepository.save(validatedClient);
    }

    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    public Client deleteClientById(Long id) {
        Optional<Client> clientToBeDeleted = clientRepository.findById(id);
        if (clientToBeDeleted.isPresent()) {
            clientRepository.delete(clientToBeDeleted.get());
        }
        return clientToBeDeleted.get();
    }

    public Client editClient(Client existingClient, Client newClient) throws InvalidClientException{
        return clientRepository.save(Validation.updateClient(existingClient, Validation.validate(newClient)));
    }

    public Client updateClient(Long id, Client client) throws InvalidClientException {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isEmpty()) {
            return createClient(client);
        } else {
            return editClient(existingClient.get(), client);
        }
    } 


}
