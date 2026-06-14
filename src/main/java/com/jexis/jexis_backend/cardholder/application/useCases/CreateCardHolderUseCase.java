package com.jexis.jexis_backend.cardholder.application.useCases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.cardholder.application.dto.CreateCardHolderDto;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.domain.exceptions.CardHolderExistsException;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;
import com.jexis.jexis_backend.stripe.application.dto.CreateStripeHolderDto;
import com.jexis.jexis_backend.stripe.application.useCases.CreateStripeHolderUseCase;
import com.stripe.exception.StripeException;

@Service
public class CreateCardHolderUseCase {
    private final CardHolderRepository repo;
    private final GetAccountUseCase getAccountUseCase;
    private final CreateStripeHolderUseCase createStripeHolder;

    public CreateCardHolderUseCase(CardHolderRepository repo, GetAccountUseCase getAccountUseCase,
            CreateStripeHolderUseCase createStripeHolder) {
        this.repo = repo;
        this.getAccountUseCase = getAccountUseCase;
        this.createStripeHolder = createStripeHolder;
    }

    public CardHolder execute(CreateCardHolderDto body) throws StripeException {
        Account account = getAccountUseCase.execute(body.accountId());
        Optional<CardHolder> existingCardHolder = repo.findByEmail(body.email());

        if (existingCardHolder.isPresent()) {
            throw new CardHolderExistsException(body.email());
        }

        createStripeHolder.execute(new CreateStripeHolderDto(
                account.getConnectAccountId(),
                body.name(),
                body.email(),
                body.phoneNumber(),
                body.addressLine1(),
                body.city(),
                body.state(),
                body.country(),
                body.postalCode()));

        CardHolder cardHolder = new CardHolder(account, body.name(), body.email(), body.phoneNumber(),
                body.addressLine1(), body.city(), body.state(), body.country(), body.postalCode());
        return repo.save(cardHolder);
    }
}
