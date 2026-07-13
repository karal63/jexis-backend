package com.jexis.jexis_backend.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * EditDto
 *
 * Data Transfer Object used for user editing requests. It encapsulates the
 * necessary data required to edit an existing user, such as the user name,
 * email,
 * and password.
 *
 * Author: Leo
 */
public class EditDto {
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
    private String firstName;

    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^[\\d+\\-()\\s]*$", message = "Invalid phone format")
    private String phoneNumber;

    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
