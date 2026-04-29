package com.java.tp.boat.rental.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientResponse {
    private Long cid;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean hasLicense;
}
