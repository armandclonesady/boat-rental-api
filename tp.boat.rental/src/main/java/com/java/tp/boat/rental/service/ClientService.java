package com.java.tp.boat.rental.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.java.tp.boat.rental.exceptions.ClientDoesNotExistException;
import com.java.tp.boat.rental.exceptions.InvalidClientException;
import com.java.tp.boat.rental.model.business.Client;
import com.java.tp.boat.rental.model.entity.ClientEntity;
import com.java.tp.boat.rental.model.request.ClientRequest;
import com.java.tp.boat.rental.model.response.ClientResponse;
import com.java.tp.boat.rental.repository.ClientRepository;
import com.java.tp.boat.rental.utils.mappers.ClientMapper;

import lombok.Data;

@Service
@Data
public class ClientService {
    
    private ClientRepository clientRepository;
    private ClientMapper clientMapper;

    public ClientResponse getClientById(Long id) throws ClientDoesNotExistException {
        return clientRepository.findById(id).map(clientMapper::toResponse).orElseThrow(() -> new ClientDoesNotExistException(String.format("No client associated with id %d", id)));
    }

    public ArrayList<ClientResponse> getAllClients() {
        Iterable<ClientEntity> clients = clientRepository.findAll();
        ArrayList<ClientResponse> clientResponses = new ArrayList<>();
        clients.forEach(client -> clientResponses.add(clientMapper.toResponse(client)));
        return clientResponses;
    }

    public ClientResponse createClient(ClientRequest clientRequest) throws InvalidClientException {
        Client client = clientMapper.toDomain(clientRequest);
        return clientMapper.toResponse(clientRepository.save(clientMapper.toEntity(client)));
    }

    public void deleteClient(ClientEntity client) {
        clientRepository.delete(client);
    }

    public ClientResponse deleteClientById(Long id) throws ClientDoesNotExistException {
        Optional<ClientEntity> clientToBeDeleted = clientRepository.findById(id);
        if (clientToBeDeleted.isPresent()) {
            clientRepository.delete(clientToBeDeleted.get());
        }
        return clientToBeDeleted.map(clientMapper::toResponse).orElseThrow(() -> new ClientDoesNotExistException(String.format("No client associated with id %d", id)));
    }

    public ClientResponse editClient(ClientRequest existingClientRequest, ClientRequest newClientRequest) throws InvalidClientException{
        Client existingClient = clientMapper.toDomain(existingClientRequest);
        existingClient.updateWith(clientMapper.toDomain(newClientRequest));
        return clientMapper.toResponse(existingClient);
    }

    public ClientResponse updateClient(Long id, ClientRequest clientRequest) throws InvalidClientException {
        Optional<ClientEntity> existingClient = clientRepository.findById(id);
        if (existingClient.isEmpty()) {
            return createClient(clientRequest);
        } else {
            Client client = clientMapper.toDomain(clientRequest);
            client.updateWith(clientMapper.toDomain(clientRequest));
            ClientEntity updatedClient = clientRepository.save(clientMapper.toEntity(client));
            return clientMapper.toResponse(updatedClient);
        }
    } 


}
