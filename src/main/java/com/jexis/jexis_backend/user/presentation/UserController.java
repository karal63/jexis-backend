package com.jexis.jexis_backend.user.presentation;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.user.application.useCases.GetUsersUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;

/**
 * UserController
 *
 * REST controller in the presentation layer responsible for exposing
 * user-related HTTP endpoints.
 *
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated user use case
 * services (application layer).
 *
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 *
 * Author: Leo
 */
@RestController
@RequestMapping("/user")
public class UserController {

    GetUsersUseCase getUsersUseCase;

    public UserController(GetUsersUseCase getUsersUseCase) {
        this.getUsersUseCase = getUsersUseCase;
    }

    /**
     * Returns a list of all users.
     *
     * This endpoint retrieves all existing users by delegating to the
     * getUsersUseCase, which interacts with the repository to fetch the data.
     *
     * Endpoint: GET /user/list
     *
     * @return list of all accounts
     */
    @GetMapping("/list")
    public List<User> getUsers() {
        return getUsersUseCase.execute();
    }

    @GetMapping("/create")
    public List<User> createUsers() {
        return getUsersUseCase.execute();
    }

    @DeleteMapping("/delete/{id}")
    public List<User> deleteUser() {
        return getUsersUseCase.execute();
    }

    @PatchMapping("/edit/{id}")
    public List<User> editwUser() {
        return getUsersUseCase.execute();
    }
}
