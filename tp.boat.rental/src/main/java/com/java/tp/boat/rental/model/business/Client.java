package com.java.tp.boat.rental.model.business;
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

    public void updateWith(Client newClient) {
        this.firstName = newClient.getFirstName() != null ? newClient.getFirstName() : this.firstName;
        this.lastName = newClient.getLastName() != null ? newClient.getLastName() : this.lastName;
        this.email = newClient.getEmail() != null ? newClient.getEmail() : this.email;
        this.phoneNumber = newClient.getPhoneNumber() != null ? newClient.getPhoneNumber() : this.phoneNumber;
        this.hasLicense = newClient.getHasLicense() != null ? newClient.getHasLicense() : this.hasLicense;
    }
}
