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
    private String name;
    private String email;
    private String password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
