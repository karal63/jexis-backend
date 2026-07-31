package com.jexis.jexis_backend.user.presentation;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import com.jexis.jexis_backend.user.application.dto.*;
import com.jexis.jexis_backend.user.application.useCases.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import jakarta.validation.Valid;

/**
 * UserController
 * <p>
 * REST controller in the presentation layer responsible for exposing
 * user-related HTTP endpoints.
 * <p>
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated user use case
 * services (application layer).
 * <p>
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 * <p>
 * Author: Leo
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    private final GetUsersUseCase getUsersUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final EditUserUseCase editUserUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DtoHelper dtoHelper;
    private final ConfirmPasswordResetUseCase confirmPasswordResetUseCase;
    private final RequestPasswordChangeUseCase requestPasswordChangeUseCase;
    private final SendActivationLinkUseCase sendActivationLinkUseCase;
    private final ActivateUserUseCase activateUserUseCase;
    private @Value("${application.origin}") String applicationOrigin;

    /**
     * Returns a list of all users.
     * <p>
     * This endpoint retrieves all existing users by delegating to the
     * getUsersUseCase, which interacts with the repository to fetch the data.
     * <p>
     * Endpoint: GET /user/list
     *
     * @return list of all accounts
     */
    @GetMapping("/admin/users")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public List<UserResponseDto> getUsers() {
        return getUsersUseCase.execute().stream().map(dtoHelper::toUserDto).toList();
    }

    /**
     * Retrieves a single user by their identifier.
     * <p>
     * Endpoint: GET /user/list/{id}
     *
     * @param id the unique identifier of the user to retrieve
     * @return the matching user entity
     */
    @GetMapping("/admin/users/{id}")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public UserResponseDto getUser(@PathVariable UUID id) {
        return dtoHelper.toUserDto(getUserUseCase.execute(id));
    }

    /**
     * Creates a new user account.
     * <p>
     * Endpoint: POST /user/create
     *
     * @param body the request payload containing user creation details
     * @return the newly created user entity
     */
    @PostMapping("/admin/users/create")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public UserResponseDto createUsers(@Valid @RequestBody AdminCreateDto body) {
        CreateDto dto = new CreateDto(body.getFirstName(), body.getLastName(), body.getEmail(), body.getPhoneNumber(),
                body.getPassword());
        return dtoHelper.toUserDto(createUserUseCase.execute(dto, body.getRoles()));
    }

    /**
     * Deletes a user by their identifier.
     * <p>
     * Endpoint: DELETE /user/delete/{id}
     *
     * @param id the unique identifier of the user to delete
     * @return a confirmation message after successful deletion
     */
    @PostMapping("/users/{id}/delete")
    @PreAuthorize("@userAuthorization.canDelete(authentication.principal.id(), #id)")
    public String deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
        return "User deleted successfully";
    }

    /**
     * Updates an existing user with the provided changes.
     * Endpoint: PATCH /user/edit/{id}
     *
     * @param editDto the user update payload
     * @param id      the unique identifier of the user to update
     * @return an optional updated user entity
     */
    @PatchMapping("/users/{id}/edit")
    @PreAuthorize("@userAuthorization.canEdit(authentication.principal.id(), #id)")
    public UserResponseDto editUser(@Valid @RequestBody EditDto editDto, @PathVariable UUID id) {
        return dtoHelper.toUserDto(editUserUseCase.execute(id, editDto));
    }

    /**
     * Changes a user's password.
     * <p>
     * Endpoint: PATCH /admin/users/{id}/password
     *
     * @param passwordDto the request payload containing the new password
     * @param id          the unique identifier of the user whose password will be updated
     * @return the updated user entity
     */
    @PatchMapping("/admin/users/{id}/password")
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    public UserResponseDto changePassword(@Valid @RequestBody ChangePasswordDto passwordDto, @PathVariable UUID id) {
        return dtoHelper.toUserDto(changePasswordUseCase.execute(id, passwordDto.getPassword()));
    }

    @PostMapping("/users/change-password")
    public ResponseEntity<String> requestPasswordReset(@Valid @RequestBody RequestPasswordChangeDto body) {
        requestPasswordChangeUseCase.execute(body);
        return ResponseEntity.ok("We sent your password reset link to your email");
    }

    @PostMapping("/users/reset-password/confirm")
    public ResponseEntity<String> confirmPasswordReset(@Valid @RequestBody ConfirmPasswordResetDto body) {
        confirmPasswordResetUseCase.execute(body);
        return ResponseEntity.ok("Password changed successfully.");
    }

    @GetMapping("/user/activate")
    public ResponseEntity<Void> sendActivationLink(@RequestParam String token) {
        activateUserUseCase.execute(token);

        String redirectUrl = "%s/dashboard".formatted(applicationOrigin);
        return ResponseEntity.status(302).location(URI.create(redirectUrl)).build();
    }
}
