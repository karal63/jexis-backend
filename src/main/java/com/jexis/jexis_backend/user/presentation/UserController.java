package com.jexis.jexis_backend.user.presentation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.application.dto.EditDto;
import com.jexis.jexis_backend.user.application.useCases.CreateUserUseCase;
import com.jexis.jexis_backend.user.application.useCases.DeleteUserUseCase;
import com.jexis.jexis_backend.user.application.useCases.EditUserUseCase;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
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
    CreateUserUseCase createUserUseCase;
    DeleteUserUseCase deleteUserUseCase;
    EditUserUseCase editUserUseCase;
    private final GetUserUseCase getUserUseCase;

    public UserController(GetUsersUseCase getUsersUseCase, CreateUserUseCase createUserUseCase,
            DeleteUserUseCase deleteUserUseCase, EditUserUseCase editUserUseCase, GetUserUseCase getUserUseCase) {
        this.getUsersUseCase = getUsersUseCase;
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.editUserUseCase = editUserUseCase;
        this.getUserUseCase = getUserUseCase;
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

    @GetMapping("/list/{id}")
    public User getUser(@PathVariable UUID id) {
        return getUserUseCase.execute(id);
    }

    @PostMapping("/create")
    public User createUsers(@RequestBody CreateDto createDto) {
        return createUserUseCase.execute(createDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
        return "User deleted successfully";
    }

    @PatchMapping("/edit/{id}")
    public Optional<User> editUser(@RequestBody EditDto editDto, @PathVariable UUID id) {
        return editUserUseCase.execute(id, editDto);
    }
}
