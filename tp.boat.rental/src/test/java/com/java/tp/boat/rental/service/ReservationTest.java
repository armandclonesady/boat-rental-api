package com.java.tp.boat.rental.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.java.tp.boat.rental.exceptions.reservation.ReservationForTooManyPeopleException;
import com.java.tp.boat.rental.model.business.Reservation;
import com.java.tp.boat.rental.model.entity.BoatEntity;
import com.java.tp.boat.rental.model.entity.BoatTypes;
import com.java.tp.boat.rental.model.entity.ClientEntity;
import com.java.tp.boat.rental.model.entity.ReservationEntity;
import com.java.tp.boat.rental.model.request.ReservationCreationRequest;
import com.java.tp.boat.rental.repository.BoatRepository;
import com.java.tp.boat.rental.repository.ClientRepository;
import com.java.tp.boat.rental.repository.ReservationRepository;
import com.java.tp.boat.rental.utils.mappers.BoatMapper;
import com.java.tp.boat.rental.utils.mappers.ClientMapper;
import com.java.tp.boat.rental.utils.mappers.ReservationMapper;

import java.sql.Date;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {

    @Mock 
    ReservationRepository reservationRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    BoatRepository boatRepository;

    private ReservationService reservationService;
    @Mock // we arent testing this service, so we can mock it
    private ClientService clientService;
    @Mock // we arent testing this service, so we can mock it
    private BoatService boatService;

    private ClientMapper clientMapper = new ClientMapper();
    private BoatMapper boatMapper = new BoatMapper();
    private ReservationMapper reservationMapper = new ReservationMapper(clientMapper, boatMapper);

    ArrayList<ReservationEntity> reservations = new ArrayList<>();
    ArrayList<BoatEntity> boats = new ArrayList<>();
    ArrayList<ClientEntity> clients = new ArrayList<>();



    private static ClientEntity clientEntity(Long id, String firstName, String lastName, Boolean hasLicense) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setCid(id); clientEntity.setFirstName(firstName);
        clientEntity.setLastName(lastName); clientEntity.setEmail("test@example.com");
        clientEntity.setPhoneNumber("0123456789"); clientEntity.setHasLicense(hasLicense);
        return clientEntity;
    }

    private static BoatEntity boatEntity(Long id, String name, Integer maxCapacity, Boolean needsLicense) {
        BoatEntity boatEntity = new BoatEntity();
        boatEntity.setBid(id); boatEntity.setName(name);
        boatEntity.setType(BoatTypes.MOTOR); boatEntity.setMaxCapacity(maxCapacity);
        boatEntity.setLength(100F); boatEntity.setNeedsLicense(needsLicense);
        boatEntity.setDailyRate(100D); boatEntity.setDeposit(50D);
        return boatEntity;
    }

    @BeforeEach
    public void setUp() {
        reservationService = new ReservationService(reservationRepository, clientService, boatService, clientMapper, boatMapper, reservationMapper);  
        clients.add(clientEntity(1L, "John", "Doe", true));
        clients.add(clientEntity(2L, "Jane", "Doe", false));
        boats.add(boatEntity(1L, "KnotWorking", 4, true));
        boats.add(boatEntity(2L, "MaybeWorking", 4, false));
    }

    @Test
    public void getAllReservationsReturnsAllReservations() {
        when(reservationRepository.findAll()).thenReturn(reservations);
        ArrayList<Reservation> reservationDomains = reservationService.getAllReservations();
        
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setRid(1L); reservationEntity.setCid(clients.get(0)); ; reservationEntity.setBid(boats.get(0));

        Assertions.assertThat(reservationDomains).hasSize(reservations.size());

        reservations.add(reservationEntity);
        reservationDomains = reservationService.getAllReservations();

        Assertions.assertThat(reservationDomains).hasSize(reservations.size());
    }

    // test bizarre? je ne sais pas si ça marche?
    @Test
    public void createReservationSavesReservation() {
        ReservationCreationRequest request = new ReservationCreationRequest();
        request.setCid(1L); request.setBid(1L); request.setAmountOfPeople(2);
        request.setStartTime(Date.valueOf(LocalDate.now().plusDays(1)));
        request.setEndTime(Date.valueOf(LocalDate.now().plusDays(2))); // good for now

        Reservation expectedReservation = new Reservation();
        expectedReservation.setClient(clientMapper.toDomainFromEntity(clients.get(0)));
        expectedReservation.setBoat(boatMapper.toDomainFromEntity(boats.get(0)));
        expectedReservation.setAmountOfPeople(request.getAmountOfPeople());
        expectedReservation.setStartTime(request.getStartTime());
        expectedReservation.setEndTime(request.getEndTime());

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setRid(1L); 
        reservationEntity.setCid(clients.get(0)); 
        reservationEntity.setBid(boats.get(0));
        reservationEntity.setAmountOfPeople(request.getAmountOfPeople()); 
        reservationEntity.setStartTime(request.getStartTime()); 
        reservationEntity.setEndTime(request.getEndTime());

        // mocking the things that are called
        when(clientService.getClientById(1L)).thenReturn(clientMapper.toDomainFromEntity(clients.get(0)));
        when(boatService.getBoatById(1L)).thenReturn(boatMapper.toDomainFromEntity(boats.get(0)));
        when(reservationRepository.save(any())).thenReturn(reservationEntity);

        Reservation reservation = reservationService.createReservation(request);

        Assertions.assertThat(reservations.size() == 1);
        Assertions.assertThat(reservation.getRid()).isEqualTo(1L);
        Assertions.assertThat(reservation.getClient().getCid()).isEqualTo(1L);
        Assertions.assertThat(reservation.getBoat().getBid()).isEqualTo(1L);
    }

    @Test
    public void createReservationWithTooManyPeopleThrowsException() {
        ReservationCreationRequest request = new ReservationCreationRequest();
        request.setCid(1L); request.setBid(1L); request.setAmountOfPeople(boats.get(0).getMaxCapacity() + 1); // too many people
        request.setStartTime(Date.valueOf(LocalDate.now().plusDays(1)));
        request.setEndTime(Date.valueOf(LocalDate.now().plusDays(2))); // good for now

        Reservation expectedReservation = new Reservation();
        expectedReservation.setClient(clientMapper.toDomainFromEntity(clients.get(0)));
        expectedReservation.setBoat(boatMapper.toDomainFromEntity(boats.get(0)));
        expectedReservation.setAmountOfPeople(request.getAmountOfPeople());
        expectedReservation.setStartTime(request.getStartTime());
        expectedReservation.setEndTime(request.getEndTime());

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setRid(1L); 
        reservationEntity.setCid(clients.get(0)); 
        reservationEntity.setBid(boats.get(0));
        reservationEntity.setAmountOfPeople(request.getAmountOfPeople()); 
        reservationEntity.setStartTime(request.getStartTime()); 
        reservationEntity.setEndTime(request.getEndTime());

        // mocking the things that are called
        when(clientService.getClientById(1L)).thenReturn(clientMapper.toDomainFromEntity(clients.get(0)));
        when(boatService.getBoatById(1L)).thenReturn(boatMapper.toDomainFromEntity(boats.get(0)));

        Assertions.assertThatExceptionOfType(ReservationForTooManyPeopleException.class).isThrownBy(() -> reservationService.createReservation(request));
    }

    
}
