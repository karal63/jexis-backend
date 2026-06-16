package com.jexis.jexis_backend.auth.application.dto;

/**
 * SignupDto
 *
 * Data Transfer Object used for signup requests. It encapsulates the
 * necessary data required to create a new user account, such as the user's
 * name, email, and password.
 *
 * Author: Leo
 */
public class SignupDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
