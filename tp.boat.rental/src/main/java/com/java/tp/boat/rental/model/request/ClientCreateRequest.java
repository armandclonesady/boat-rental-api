package com.java.tp.boat.rental.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientCreateRequest {
    @Null
    private Long cid;

    @NotBlank(message = "First name cannot be blank")
    @NotNull(message = "First name cannot be null")
    @Size(message = "First name cannot be longer than 100 characters", max = 100)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(message = "Last name cannot be longer than 100 characters", max = 100)
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Size(message = "Email cannot be longer than 100 characters", max = 100)
    private String email;

    @Size(message = "Phone number cannot be longer than 10 characters", max = 10)
    private String phoneNumber;

    private Boolean hasLicense = false;
}
