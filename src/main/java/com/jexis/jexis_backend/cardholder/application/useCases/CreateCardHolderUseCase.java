package com.jexis.jexis_backend.cardholder.application.useCases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.cardholder.application.dto.CreateCardHolderDto;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.domain.exceptions.CardHolderExistsException;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.stripe.application.dto.CreateStripeHolderDto;
import com.jexis.jexis_backend.stripe.application.useCases.CreateStripeHolderUseCase;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.stripe.exception.StripeException;
import com.stripe.model.issuing.Cardholder;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CreateCardHolderUseCase {
    private final CardHolderRepository repo;
    private final GetAccountUseCase getAccountUseCase;
    private final CreateStripeHolderUseCase createStripeHolder;
    private final GetUserUseCase getUserUseCase;

    public CreateCardHolderUseCase(CardHolderRepository repo, GetAccountUseCase getAccountUseCase,
            CreateStripeHolderUseCase createStripeHolder, GetUserUseCase getUserUseCase, AsyncLogger logger) {
        this.repo = repo;
        this.getAccountUseCase = getAccountUseCase;
        this.createStripeHolder = createStripeHolder;
        this.getUserUseCase = getUserUseCase;
    }

    public CardHolder execute(CreateCardHolderDto body, HttpServletRequest request) throws StripeException {
        Account account = getAccountUseCase.execute(body.accountId());
        User user = getUserUseCase.execute(body.userId());
        Optional<CardHolder> existingCardHolder = repo.findByEmailAndAccountId(user.getEmail(), account.getId());

        if (existingCardHolder.isPresent()) {
            throw new CardHolderExistsException(user.getEmail());
        }

        // TODO replace phone number when i add it to user entity
        Cardholder stripeCardHolder = createStripeHolder.execute(request, new CreateStripeHolderDto(
                account.getConnectAccountId(),
                user.getName(),
                user.getEmail(),
                "+18888675309",
                body.addressLine1(),
                body.city(),
                body.state(),
                body.country(),
                body.postalCode()));

        // TODO remove user.getName because we have name in user object
        CardHolder cardHolder = new CardHolder(stripeCardHolder.getId(), account, user, user.getName(), user.getEmail(),
                "+18888675309",
                body.addressLine1(), body.city(), body.state(), body.country(),
                body.postalCode());

        return repo.save(cardHolder);
    }
}
