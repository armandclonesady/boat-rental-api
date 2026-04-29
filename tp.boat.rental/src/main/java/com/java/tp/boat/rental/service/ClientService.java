package com.java.tp.boat.rental.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.InvalidClientException;
import com.java.tp.boat.rental.model.entity.ClientEntity;
import com.java.tp.boat.rental.repository.ClientRepository;
import com.java.tp.boat.rental.utils.Validation;

import lombok.Data;

@Service
@Data
public class ClientService {
    
    @Autowired
    private ClientRepository clientRepository;

    public Optional<ClientEntity> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Iterable<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    public ClientEntity createClient(ClientEntity client) throws InvalidClientException {
        ClientEntity validatedClient = Validation.validate(client);
        return clientRepository.save(validatedClient);
    }

    public void deleteClient(ClientEntity client) {
        clientRepository.delete(client);
    }

    public ClientEntity deleteClientById(Long id) {
        Optional<ClientEntity> clientToBeDeleted = clientRepository.findById(id);
        if (clientToBeDeleted.isPresent()) {
            clientRepository.delete(clientToBeDeleted.get());
        }
        return clientToBeDeleted.get();
    }

    public ClientEntity editClient(ClientEntity existingClient, ClientEntity newClient) throws InvalidClientException{
        return clientRepository.save(Validation.updateClient(existingClient, Validation.validate(newClient)));
    }

    public ClientEntity updateClient(Long id, ClientEntity client) throws InvalidClientException {
        Optional<ClientEntity> existingClient = clientRepository.findById(id);
        if (existingClient.isEmpty()) {
            return createClient(client);
        } else {
            return editClient(existingClient.get(), client);
        }
    } 


}
