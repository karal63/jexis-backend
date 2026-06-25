package com.jexis.jexis_backend.user.application.dto;

import java.util.List;

import com.jexis.jexis_backend.user.domain.enums.UserRole;

/**
 * CreateDto
 *
 * Data Transfer Object used for user creation requests. It encapsulates the
 * necessary data required to create a new user, such as the user name, email,
 * and password.
 *
 * Author: Leo
 */
public class AdminCreateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<UserRole> roles;

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

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
