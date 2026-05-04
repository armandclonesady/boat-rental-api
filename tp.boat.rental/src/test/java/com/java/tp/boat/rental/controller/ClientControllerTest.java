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


@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Long addedClientId;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws Exception {
        addedClientId = null;
    }

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/clients/"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetClientByIdWorks() throws Exception {
        addedClientId = createTestClient();
        String response = mockMvc.perform(get("/clients/" + addedClientId))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
        Long responseId = objectMapper.readTree(response).get("cid").asLong();
        assert(responseId.equals(addedClientId));
    }

    @Test
    public void testPostClientWorks() throws Exception {
        addedClientId = createTestClient();
        String response = mockMvc.perform(get("/clients/" + addedClientId))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);
        Long responseId = objectMapper.readTree(response).get("cid").asLong();
        assert(responseId.equals(addedClientId));
    }

    @Test
    public void testUpdateClientWorks() throws Exception {
        addedClientId = createTestClient();
        String response = mockMvc.perform(put("/clients/update/" + addedClientId)
        .contentType("application/json")
        .content(
            "{ \"email\": \"test@testexample.com\" }"
        ))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(response);

        String testPostUpdateResponse = mockMvc.perform(get("/clients/" + addedClientId))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(testPostUpdateResponse);

        Long responseId = objectMapper.readTree(response).get("cid").asLong();
        assert(responseId.equals(addedClientId));
    }
    

    @Test
    public void testDeleteClientWorks() throws Exception {
        addedClientId = createTestClient();
        mockMvc.perform(delete("/clients/delete/" + addedClientId))
        .andExpect(status().isOk());
        mockMvc.perform(get("/clients/" + addedClientId))
        .andExpect(status().isNotFound());
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
}
