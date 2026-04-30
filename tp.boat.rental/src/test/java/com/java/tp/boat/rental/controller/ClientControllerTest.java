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

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Long addedClientId;

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/clients/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testPostClientWorks() throws Exception {
        // First, add a client to ensure there is at least one client in the database
        mockMvc.perform(post("/clients/")
        .contentType("application/json")
        .content(
            "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phoneNumber\": \"1234567890\" }"
        ))
        .andExpect(status().isCreated())
        .andDo(result -> {
            String response = result.getResponse().getContentAsString();
            String idString = response.split("\"cid\":")[1].split(",")[0].trim();
            addedClientId = Long.parseLong(idString);
        });
        if (addedClientId != null) {
            mockMvc.perform(get("/clients/" + addedClientId))
            .andExpect(status().isOk()).andExpect(jsonPath("$.cid").value(addedClientId));
        }
    }
    
    @Test
    public void testPostClientFailsWithMissingData() throws Exception {
        mockMvc.perform(post("/clients/")
        .contentType("application/json")
        .content(
            "{ \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phone\": \"1234567890\" }"
        ))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostClientFailsWithBlankData() throws Exception {
        mockMvc.perform(post("/clients/")
        .contentType("application/json")
        .content(
            "{ \"firstName\": \"\", \"lastName\": \"Doe\", \"email\": \"john.doe@example.com\", \"phone\": \"1234567890\" }"
        ))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostClientFailsWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/clients/")
        .contentType("application/json")
        .content(
            "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"john.doeexample.com\", \"phone\": \"1234567890\" }"
        ))
        .andExpect(status().isBadRequest());
    }
}
