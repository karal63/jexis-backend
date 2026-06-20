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
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.application.dto.EditDto;
import com.jexis.jexis_backend.user.application.dto.UserResponseDto;
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
    private final DtoHelper dtoHelper;

    public UserController(GetUsersUseCase getUsersUseCase, CreateUserUseCase createUserUseCase,
            DeleteUserUseCase deleteUserUseCase, EditUserUseCase editUserUseCase, GetUserUseCase getUserUseCase,
            DtoHelper dtoHelper) {
        this.getUsersUseCase = getUsersUseCase;
        this.createUserUseCase = createUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.editUserUseCase = editUserUseCase;
        this.getUserUseCase = getUserUseCase;
        this.dtoHelper = dtoHelper;
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
    public List<UserResponseDto> getUsers() {
        return getUsersUseCase.execute().stream().map(dtoHelper::toUserDto).toList();
    }

    /**
     * Retrieves a single user by their identifier.
     *
     * Endpoint: GET /user/list/{id}
     *
     * @param id the unique identifier of the user to retrieve
     * @return the matching user entity
     */
    @GetMapping("/list/{id}")
    public UserResponseDto getUser(@PathVariable UUID id) {
        return dtoHelper.toUserDto(getUserUseCase.execute(id));
    }

    /**
     * Creates a new user account.
     *
     * Endpoint: POST /user/create
     *
     * @param createDto the request payload containing user creation details
     * @return the newly created user entity
     */
    @PostMapping("/create")
    public UserResponseDto createUsers(@RequestBody CreateDto createDto) {
        return dtoHelper.toUserDto(createUserUseCase.execute(createDto));
    }

    /**
     * Deletes a user by their identifier.
     *
     * Endpoint: DELETE /user/delete/{id}
     *
     * @param id the unique identifier of the user to delete
     * @return a confirmation message after successful deletion
     */
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
        return "User deleted successfully";
    }

    /**
     * Updates an existing user with the provided changes.
     *
     * Endpoint: PATCH /user/edit/{id}
     *
     * @param editDto the user update payload
     * @param id      the unique identifier of the user to update
     * @return an optional updated user entity
     */
    @PatchMapping("/edit/{id}")
    public UserResponseDto editUser(@RequestBody EditDto editDto, @PathVariable UUID id) {
        return dtoHelper.toUserDto(editUserUseCase.execute(id, editDto));
    }
}
