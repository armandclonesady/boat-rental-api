package com.java.tp.boat.rental.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class ReservationControllerTest {
     @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long addedBoatId;
    private Long addedClientId;
    private Long addedReservationId;

    @BeforeEach
    public void setup() throws Exception {
        addedBoatId = null;
        addedClientId = null;
        addedReservationId = null;
    }

    @Test
    public void testGetAllReservations() throws Exception {
        mockMvc.perform(get("/reservations/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetReservationByIdWorks() throws Exception {
        addedBoatId = createTestBoat();
        addedClientId = createTestClient();
        addedReservationId = createTestReservation(addedBoatId, addedClientId);
        String response = mockMvc.perform(get("/reservations/" + addedReservationId))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
        Long responseId = objectMapper.readTree(response).get("rid").asLong();
        assert(responseId.equals(addedReservationId));
     }

    private Long createTestBoat() throws Exception {
        String response = mockMvc.perform(post("/boats/create")
        .contentType("application/json")
        .content(
            "{ \"name\": \"Knot Working\", \"type\": \"SAILING\", \"maxCapacity\": 4, \"length\": 30.5 }"
         ))
         .andExpect(status().isCreated())
         .andReturn().getResponse().getContentAsString();
        System.out.println("/!\\ Created boat with response: " + response);
        Long responseId = objectMapper.readTree(response).get("bid").asLong();
        System.out.println("/!\\ Created boat with id: " + responseId);
        return responseId;
    }

    private Long createTestClient() throws Exception {
        String response = mockMvc.perform(post("/clients/create")
        .contentType("application/json")
        .content(
            "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phoneNumber\": \"1234567890\" }"
        ))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
        System.out.println("/!\\ Created client with response: " + response);
        Long responseId = objectMapper.readTree(response).get("cid").asLong();
        System.out.println("/!\\ Created client with id: " + responseId);
        return responseId;
    }

    private Long createTestReservation(Long boatId, Long clientId) throws Exception {
        String response = mockMvc.perform(post("/reservations/create")
        .contentType("application/json")
        .content(
            String.format("{ \"boatId\": %d, \"clientId\": %d, \"startDate\": \"2024-07-01\", \"endDate\": \"2024-07-10\" }", boatId, clientId)
         ))
         .andExpect(status().isCreated())
         .andReturn().getResponse().getContentAsString();
        System.out.println("/!\\ Created reservation with response: " + response);
        Long responseId = objectMapper.readTree(response).get("rid").asLong();
        System.out.println("/!\\ Created reservation with id: " + responseId);
        return responseId;
    }
}
