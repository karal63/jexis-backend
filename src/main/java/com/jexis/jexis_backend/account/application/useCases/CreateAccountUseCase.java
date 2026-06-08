package com.jexis.jexis_backend.account.application.useCases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.dto.CreateAccountDto;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.stripe.application.useCases.CreateConnectUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.CreateLinkUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.domain.exception.EmailExistsException;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.stripe.exception.StripeException;
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

    public CreateAccountUseCase(AccountRepository repo, AsyncLogger logger, CreateConnectUseCase createConnectUseCase,
            CreateLinkUseCase createLinkUseCase) {
        this.repo = repo;
        this.logger = logger;
        this.createConnectUseCase = createConnectUseCase;
        this.createLinkUseCase = createLinkUseCase;
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
    public Account execute(CreateAccountDto body, User owner) throws StripeException {
        logger.info("ACCOUNT", "Creating account with email: " + body.getEmail());

        Optional<Account> existingAccount = repo.findByEmail(body.getEmail());
        if (existingAccount.isPresent()) {
            logger.info("ACCOUNT", "Account creation failed: email already exists " + body.getEmail());
            throw new EmailExistsException(body.getEmail());
        }

        com.stripe.model.Account connectAccount = createConnectUseCase.execute(body.getEmail());
        AccountLink link = createLinkUseCase.execute(connectAccount.getId());

        Account account = new Account(body.getEmail(), connectAccount.getId(), link.getUrl(), owner);
        Account saved = repo.save(account);

        logger.info("ACCOUNT", "Account created successfully: " + saved.getName());
        return saved;
    }
}