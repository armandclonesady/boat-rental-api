package com.java.tp.boat.rental.model.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientUpdateRequest {
    @Null
    private Long cid;

    @Size(message = "First name cannot be longer than 100 characters", max = 100)
    private String firstName;

    @Size(message = "Last name cannot be longer than 100 characters", max = 100)
    private String lastName;

    @Email(message = "Email must be a valid email address", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Size(message = "Email cannot be longer than 100 characters", max = 100)
    private String email;

    @Size(message = "Phone number cannot be longer than 10 characters", max = 10)
    private String phoneNumber;

    private Boolean hasLicense = false;
}
