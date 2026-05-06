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

    private ClientMapper clientMapper = new ClientMapper();

    private ClientService clientService;

    private static ClientCreationRequest clientCreationRequest(Long id, String firstName, String lastName) {
        ClientCreationRequest clientCreationRequest = new ClientCreationRequest();
        clientCreationRequest.setFirstName(firstName);
        clientCreationRequest.setLastName(lastName);
        clientCreationRequest.setEmail("test@example.com");
        clientCreationRequest.setPhoneNumber("0123456789");
        clientCreationRequest.setHasLicense(false);
        return clientCreationRequest;
    }

    private static Client clientDomain(Long id, String firstName, String lastName) { return new Client(id, firstName, lastName, "test@example.com", "0123456789", false); };

    private static ClientEntity clientEntity(Long id, String firstName, String lastName) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setCid(id);
        clientEntity.setFirstName(firstName);
        clientEntity.setLastName(lastName);
        clientEntity.setEmail("test@example.com");
        clientEntity.setPhoneNumber("0123456789");
        clientEntity.setHasLicense(false);
        return clientEntity;
    }

    @BeforeEach
    public void setUp() {
        clientService = new ClientService(clientRepository, clientMapper);
    }

    @Test
    public void getAllClientsReturnsListOfClients() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setCid(1L);
        ArrayList<ClientEntity> clientEntities = new ArrayList<>();
        clientEntities.add(clientEntity);
        when(clientRepository.findAll()).thenReturn(clientEntities);
        ArrayList<Client> clients = clientService.getAllClients();
        Assertions.assertThat(clients).hasSize(1);
        Assertions.assertThat(clients.get(0).getCid()).isEqualTo(1L);
    }

    @Test
    public void createClientSavesClient() {
        ClientCreationRequest request = clientCreationRequest(null, "John", "Doe");
        Client expectedClient = clientDomain(null, "John", "Doe");
        ClientEntity clientEntity = clientEntity(null, "John", "Doe");
        
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        Client createdClient = clientService.createClient(request);

        Assertions.assertThat(createdClient).isEqualTo(expectedClient);

    }
}