package com.jexis.jexis_backend.user.application.dto;

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
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
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

    public String getPhoneNUmber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
