package com.java.tp.boat.rental.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.tp.boat.rental.model.business.Client;
import com.java.tp.boat.rental.model.entity.ClientEntity;
import com.java.tp.boat.rental.model.request.ClientCreationRequest;
import com.java.tp.boat.rental.repository.ClientRepository;
import com.java.tp.boat.rental.utils.mappers.ClientMapper;

import org.assertj.core.api.Assertions;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void testGetAllClients() {
        ClientEntity clientEntity1 = new ClientEntity(); 
        clientEntity1.setCid(1L);
        
        ClientEntity clientEntity2 = new ClientEntity(); 
        clientEntity2.setCid(2L);
        
        when(clientMapper.toDomainFromEntity(clientEntity1)).thenReturn(new Client(1L, "John", "Doe", "john.doe@example.com", "1234567890", true));
        when(clientMapper.toDomainFromEntity(clientEntity2)).thenReturn(new Client(2L, "John", "Doe", "john.doe@example.com", "1234567890", true));
        ArrayList<ClientEntity> clientEntities = new ArrayList<>(); 
        clientEntities.add(clientEntity1); 
        clientEntities.add(clientEntity2);
        when(clientRepository.findAll()).thenReturn(clientEntities);
        
        ArrayList<Client> clients = clientService.getAllClients();

        Assertions.assertThat(clients.size()).isEqualTo(2);
        Assertions.assertThat(clients.get(0).getCid()).isEqualTo(1L);
        Assertions.assertThat(clients.get(1).getCid()).isEqualTo(2L);

        verify(clientMapper, times(1)).toDomainFromEntity(clientEntity1);
        verify(clientMapper, times(1)).toDomainFromEntity(clientEntity2);
    }

    @Test
    public void testGetAllClientsEmpty() {
        when(clientRepository.findAll()).thenReturn(new ArrayList<>());

        ArrayList<Client> clients = clientService.getAllClients();

        Assertions.assertThat(clients.size()).isEqualTo(0);
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    public void createClient() {
        Client client = new Client(1L, "John", "Doe", "john.doe@example.com", "1234567890", true);
        
        ClientCreationRequest clientCreationRequest = new ClientCreationRequest();
        clientCreationRequest.setFirstName("John");
        clientCreationRequest.setLastName("Doe");
        clientCreationRequest.setEmail("johndoe@gmail.com");
        
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setCid(1L);

        when(clientMapper.toDomainFromRequestCreation(clientCreationRequest)).thenReturn(new Client(1L, "John", "Doe", "john.doe@example.com", "1234567890", true));
        when(clientRepository.save(clientMapper.toEntityFromDomain(clientMapper.toDomainFromRequestCreation(clientCreationRequest)))).thenReturn(clientEntity);
        when(clientMapper.toDomainFromEntity(clientEntity)).thenReturn(client);

        Client createdClient = clientService.createClient(clientCreationRequest);
        Assertions.assertThat(createdClient.getCid()).isEqualTo(1L);
    }
}