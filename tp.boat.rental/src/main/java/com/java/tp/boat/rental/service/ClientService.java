package com.java.tp.boat.rental.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.client.ClientDoesNotExistException;
import com.java.tp.boat.rental.model.business.Client;
import com.java.tp.boat.rental.model.entity.ClientEntity;
import com.java.tp.boat.rental.model.request.ClientCreationRequest;
import com.java.tp.boat.rental.model.request.ClientUpdateRequest;
import com.java.tp.boat.rental.repository.ClientRepository;
import com.java.tp.boat.rental.utils.mappers.ClientMapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
@Data
@AllArgsConstructor
public class ClientService {
    
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public Client getClientById(Long id) {
        return clientRepository.findById(id).map(clientMapper::toDomainFromEntity).orElseThrow(() -> new ClientDoesNotExistException(String.format("No client associated with id %d", id)));
    }

    public ArrayList<Client> getAllClients() {
        Iterable<ClientEntity> clients = clientRepository.findAll();
        ArrayList<Client> clientDomains = new ArrayList<>();
        clients.forEach(client -> clientDomains.add(clientMapper.toDomainFromEntity(client)));
        return clientDomains;
    }

    public Client createClient(ClientCreationRequest clientRequest) {
        Client client = clientMapper.toDomainFromRequestCreation(clientRequest);
        clientRepository.save(clientMapper.toEntityFromDomain(client));
        return client;
    }

    public Client deleteClientById(Long id) {
        Optional<ClientEntity> clientToBeDeleted = clientRepository.findById(id);
        if (clientToBeDeleted.isPresent()) {
            clientRepository.delete(clientToBeDeleted.get());
        }
        return clientToBeDeleted.map(clientMapper::toDomainFromEntity).orElseThrow(() -> new ClientDoesNotExistException(String.format("No client associated with id %d", id)));
    }

    public Client editClient(ClientCreationRequest existingClientRequest, ClientCreationRequest newClientRequest) {
        Client existingClient = clientMapper.toDomainFromRequestCreation(existingClientRequest);
        existingClient.updateWith(clientMapper.toDomainFromRequestCreation(newClientRequest));
        clientRepository.save(clientMapper.toEntityFromDomain(existingClient));
        return existingClient;
    }

    public Client updateClient(Long id, ClientUpdateRequest clientUpdateRequest) {
        Optional<ClientEntity> existingClient = clientRepository.findById(id);
        Client existingClientDomain = clientMapper.toDomainFromEntity(existingClient.get());
        ClientCreationRequest clientRequest = clientMapper.toRequestCreationFromRequestUpdate(clientUpdateRequest);
        Client client = clientMapper.toDomainFromRequestCreation(clientRequest);
        existingClientDomain.updateWith(client);
        clientRepository.save(clientMapper.toEntityFromDomain(existingClientDomain));
        return existingClientDomain;
    } 


}
