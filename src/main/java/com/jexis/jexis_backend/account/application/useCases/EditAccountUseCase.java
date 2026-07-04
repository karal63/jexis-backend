package com.jexis.jexis_backend.account.application.useCases;

import java.util.Optional;
import java.util.UUID;

import com.jexis.jexis_backend.stripe.application.useCases.RetrieveAccountByIdUseCase;
import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.dto.AccountResponseDto;
import com.jexis.jexis_backend.account.application.dto.EditAccountDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.domain.exception.AccountNotFoundException;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;

/**
 * EditAccountUseCase
 * This service class implements the use case for editing an existing account.
 * It contains the business logic related to updating an account, such as
 * interacting with the repository to fetch and save the updated data, and
 * validating if user with given id exists.
 * Author: Leo
 */
@Service
public class EditAccountUseCase {
    AccountRepository accountRepo;
    UserRepository userRepo;
    private final DtoHelper dtoHelper;
    private final GetAccountUseCase getAccountUseCase;
    private final RetrieveAccountByIdUseCase retrieveAccountByIdUseCase;

    public EditAccountUseCase(AccountRepository accountRepo, UserRepository userRepo, DtoHelper dtoHelper, GetAccountUseCase getAccountUseCase,
                              RetrieveAccountByIdUseCase retrieveAccountByIdUseCase) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
        this.dtoHelper = dtoHelper;
        this.getAccountUseCase = getAccountUseCase;
        this.retrieveAccountByIdUseCase = retrieveAccountByIdUseCase;
    }

    /**
     * Handles account editing.
     * Accepts a {@link EditAccountDto} payload and {@param id} from controller,
     * looks for
     * the account, calls the repository to update the account, and returns
     * the updated {@link Account}.
     *
     * @param accountId the ID of the account to edit
     * @return the updated account
     */
    public void execute(String accountId) {
        com.stripe.model.Account stripeAccount = retrieveAccountByIdUseCase.execute(accountId);
        Account dbAccount = accountRepo.findByConnectAccountId(stripeAccount.getId()).orElseThrow(AccountNotFoundException::new);

        dbAccount.setFirstName(stripeAccount.getIndividual().getFirstName());
        dbAccount.setLastName(stripeAccount.getIndividual().getLastName());
        dbAccount.setCity(stripeAccount.getCompany().getAddress().getCity());
        dbAccount.setCountry(stripeAccount.getCompany().getAddress().getCountry());
        dbAccount.setLine1(stripeAccount.getCompany().getAddress().getLine1());
        dbAccount.setLine2(stripeAccount.getCompany().getAddress().getLine2());
        dbAccount.setPostalCode(stripeAccount.getCompany().getAddress().getPostalCode());
        dbAccount.setState(stripeAccount.getCompany().getAddress().getState());
        dbAccount.setPhone(stripeAccount.getCompany().getPhone());
        dbAccount.setEmail(stripeAccount.getEmail());

        Account newAccount = accountRepo.save(dbAccount);
    }
}
