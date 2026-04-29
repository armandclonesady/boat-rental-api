package com.java.tp.boat.rental.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientRequest {
    private Long cid;

    @NotBlank(message = "First name cannot be blank")
    @Max(message = "First name cannot be longer than 100 characters", value = 100)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Max(message = "Last name cannot be longer than 100 characters", value = 100)
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    @Max(message = "Email cannot be longer than 100 characters", value = 100)
    private String email;

    @Max(message = "Phone number cannot be longer than 10 characters", value = 10)
    private String phoneNumber;

    private Boolean hasLicense = false;
}
