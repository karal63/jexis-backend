package com.jexis.jexis_backend.user.application.dto;

/**
 * CreateDto
 *
 * Data Transfer Object used for user creation requests. It encapsulates the
 * necessary data required to create a new user, such as the user name, email,
 * and password.
 *
 * Author: Leo
 */
public class CreateDto {
    private String firstName;
    private String lastName;
    private String email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
