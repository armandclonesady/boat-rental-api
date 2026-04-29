package com.java.tp.boat.rental.model.response;

import lombok.Data;

@Data
public class ClientResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean hasLicense;
}
