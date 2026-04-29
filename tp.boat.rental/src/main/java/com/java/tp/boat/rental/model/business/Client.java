package com.java.tp.boat.rental.model.business;

import com.java.tp.boat.rental.model.response.ClientResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private Long cid;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean hasLicense;
    public ClientResponse updateWith(Client newClient) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateWith'");
    }
}
