package com.java.tp.boat.rental.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.tp.boat.rental.exceptions.client.ClientDoesNotExistException;
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

    
    @Test
    public void getClientByIdReturnsClient() {
        ClientEntity clientEntity = clientEntity(1L, "John", "Doe");
        Client expectedClient = clientDomain(1L, "John", "Doe");
        
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        Client foundClient = clientService.getClientById(1L);
        Assertions.assertThat(foundClient).isEqualTo(expectedClient);
    }

    @Test
    public void getClientByIdThrowsExceptionWhenClientDoesNotExist() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> clientService.getClientById(1L))
                .isInstanceOf(ClientDoesNotExistException.class);
    }

    @Test
    public void deleteClientByIdDeletesClient() {
        ClientEntity clientEntity = clientEntity(1L, "John", "Doe");
        Client expectedClient = clientDomain(1L, "John", "Doe");
        
        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientEntity));

        Client deletedClient = clientService.deleteClientById(1L);
       
        Assertions.assertThat(deletedClient).isEqualTo(expectedClient);
        verify(clientRepository, times(1)).delete(clientEntity);
    }

    @Test
    public void deleteClientByIdThrowsExceptionWhenClientDoesNotExist() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> clientService.deleteClientById(1L))
                .isInstanceOf(ClientDoesNotExistException.class);
    }

    @Test
    public void editClientUpdatesClient() {
        ClientCreationRequest request = clientCreationRequest(null, "John", "Doe");
        ClientEntity clientEntity = clientEntity(null, "Jane", "Doe");

        Client expectedClient = clientDomain(null, "Jane", "Doe");
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);
        
        Client editedClient = clientService.editClient(request, clientCreationRequest(null, "Jane", "Doe"));

        Assertions.assertThat(editedClient).isEqualTo(expectedClient); 
    }
}