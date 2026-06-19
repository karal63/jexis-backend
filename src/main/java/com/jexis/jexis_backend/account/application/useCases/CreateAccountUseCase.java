package com.jexis.jexis_backend.account.application.useCases;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.stripe.application.useCases.CreateConnectUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.CreateLinkUseCase;
import com.jexis.jexis_backend.account.application.dto.AccountResponseDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;
import com.jexis.jexis_backend.user.application.dto.UserResponseDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.stripe.model.AccountLink;

/**
 * CreateAccountUseCase
 *
 * This service class implements the use case for creating a new account.
 * It contains only the business logic related to account creation, such as
 * validating input data and interacting with the repository to persist the new
 * account.
 *
 * Author: Leo
 */
@Service
public class CreateAccountUseCase {
    private final AccountRepository repo;
    private final AsyncLogger logger;
    private final CreateConnectUseCase createConnectUseCase;
    private final CreateLinkUseCase createLinkUseCase;
    private final DtoHelper dtoHelper;

    public CreateAccountUseCase(AccountRepository repo, AsyncLogger logger, CreateConnectUseCase createConnectUseCase,
            CreateLinkUseCase createLinkUseCase, DtoHelper dtoHelper) {
        this.repo = repo;
        this.logger = logger;
        this.createConnectUseCase = createConnectUseCase;
        this.createLinkUseCase = createLinkUseCase;
        this.dtoHelper = dtoHelper;
    }

    /**
     * Handles account creation.
     *
     * Accepts a {@link CreateAccountDto} payload from controller, looks for
     * duplicate account name, calls the repository to save the account, and returns
     * the created {@link Account}.
     *
     * @param body passed by controller payload containing account creation data
     * @return the newly created account
     */
    public AccountResponseDto execute(User owner) {
        logger.info("ACCOUNT", "Creating account");

        com.stripe.model.Account connectAccount = createConnectUseCase.execute(owner.getEmail());
        AccountLink link = createLinkUseCase.execute(connectAccount.getId());

        Account account = new Account(owner.getEmail(), connectAccount.getId(), link.getUrl(), owner);
        Account saved = repo.save(account);

        logger.info("ACCOUNT", "Account created successfully: " + saved.getName());

        return new AccountResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getConnectAccountId(),
                saved.getAccountLink(),
                dtoHelper.toUserDto(owner),
                saved.getCreatedAt(),
                saved.getUpdatedAt());
    }
}
