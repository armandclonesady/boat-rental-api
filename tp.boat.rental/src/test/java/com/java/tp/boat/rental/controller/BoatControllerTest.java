package com.java.tp.boat.rental.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class BoatControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    private Long addedBoatId;

    @Test
    public void testGetAllBoats() throws Exception {
        mockMvc.perform(get("/boats/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testPostBoatWorks() throws Exception {
        // First, add a boat to ensure there is at least one boat in the database
        mockMvc.perform(post("/boats/")
        .contentType("application/json")
        .content(
            "{ \"name\": \"Knot Working\", \"type\": \"SAILING\", \"maxCapacity\": 4, \"length\": 30.5 }"
        ))
        .andExpect(status().isCreated())
        .andDo(result -> {
            String response = result.getResponse().getContentAsString();
            String idString = response.split("\"bid\":")[1].split(",")[0].trim();
            addedBoatId = Long.parseLong(idString);
        });
        if (addedBoatId != null) {
            mockMvc.perform(get("/boats/" + addedBoatId))
            .andExpect(status().isOk()).andExpect(jsonPath("$.bid").value(addedBoatId));
        }
    }

    @Test
    public void testPostBoatFailsWithInvalidData() throws Exception {
        mockMvc.perform(post("/boats/")
        .contentType("application/json")
        .content(
            "{ \"name\": \"\", \"type\": null, \"maxCapacity\": -1, \"length\": -5.0 }"
        ))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBoatFailsWithMissingData() throws Exception {
        mockMvc.perform(post("/boats/")
        .contentType("application/json")
        .content(
            "{ \"type\": \"SAILING\", \"maxCapacity\": 4, \"length\": 30.5 }"
        ))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostBoatFailsWithInvalidBoatType() throws Exception {
        mockMvc.perform(post("/boats/")
        .contentType("application/json")
        .content(
            "{ \"name\": \"Knot Working\", \"type\": \"CAR\", \"maxCapacity\": 4, \"length\": 30.5 }"
        ))
        .andExpect(status().isBadRequest());
    }
}
