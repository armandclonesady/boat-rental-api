package com.java.tp.boat.rental.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
public class BoatControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long addedBoatId;

    @BeforeEach
    public void setup() throws Exception {
        addedBoatId = null;
    }

    @Test
    public void testGetAllBoats() throws Exception {
        mockMvc.perform(get("/boats/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetBoatByIdWorks() throws Exception {
        addedBoatId = createTestBoat();
        String response = mockMvc.perform(get("/boats/" + addedBoatId))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
        Long responseId = objectMapper.readTree(response).get("bid").asLong();
        assert(responseId.equals(addedBoatId));
    }

    @Test
    public void testPostBoatWorks() throws Exception {
        addedBoatId = createTestBoat();
        String response = mockMvc.perform(get("/boats/" + addedBoatId))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
        Long responseId = objectMapper.readTree(response).get("bid").asLong();
        assert(responseId.equals(addedBoatId));
    }

    @Test
    public void testUpdateBoatWorks() throws Exception {
        addedBoatId = createTestBoat();
        String response = mockMvc.perform(put("/boats/update/" + addedBoatId)
        .contentType("application/json")
        .content(
            "{ \"type\": \"MOTOR\"}"
        ))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);

        String testPostUpdateResponse = mockMvc.perform(get("/boats/" + addedBoatId))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(testPostUpdateResponse);

        Long responseId = objectMapper.readTree(response).get("bid").asLong();
        assert(responseId.equals(addedBoatId));
    }

    @Test
    public void testDeleteBoatWorks() throws Exception {
        addedBoatId = createTestBoat();
        mockMvc.perform(delete("/boats/delete/" + addedBoatId))
        .andExpect(status().isOk());

        mockMvc.perform(get("/boats/" + addedBoatId))
        .andExpect(status().isNotFound());
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
}
