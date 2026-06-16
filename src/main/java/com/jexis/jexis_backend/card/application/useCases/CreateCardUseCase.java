package com.jexis.jexis_backend.card.application.useCases;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.account.infrastructure.AccountRepository;
import com.jexis.jexis_backend.card.application.dto.CreateCardDto;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.stripe.application.useCases.CreateStripeCardUseCase;
import com.jexis.jexis_backend.user.application.dto.CreateDto;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.user.infrastructure.UserRepository;
import com.jexis.jexis_backend.wallet.application.useCases.GetWalletUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import com.jexis.jexis_backend.wallet.infrastructure.WalletRepository;
import com.stripe.exception.StripeException;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.cardholder.application.useCases.GetCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;

/**
 * CreateCardUseCase
 *
 * This service class implements the use case for creating a new card.
 * It contains only the business logic related to card creation, such as
 * validating input data and interacting with the repository to persist the new
 * card.
 *
 * Author: Leo
 */
@Service
public class CreateCardUseCase {
    private final CardRepository cardRepo;
    private final AsyncLogger logger;
    private final CreateStripeCardUseCase createStripeCard;
    private final GetCardHolderUseCase getCardHolderUseCase;
    private final GetWalletUseCase getWalletUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final GetUserUseCase getUserUseCase;

    public CreateCardUseCase(CardRepository cardRepo, UserRepository userRepo, AsyncLogger logger,
            CreateStripeCardUseCase createStripeCard, GetCardHolderUseCase getCardHolderUseCase,
            GetWalletUseCase getWalletUseCase,
            GetAccountUseCase getAccountUseCase, GetUserUseCase getUserUseCase) {
        this.cardRepo = cardRepo;
        this.logger = logger;
        this.createStripeCard = createStripeCard;
        this.getCardHolderUseCase = getCardHolderUseCase;
        this.getWalletUseCase = getWalletUseCase;
        this.getAccountUseCase = getAccountUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    /**
     * Creates a new card
     *
     * Accepts a {@link CreateDto} payload from controller, creates a new card,
     * and returns the created card.
     *
     * @param dto the data transfer object containing card creation details
     * @return the created card entity
     */
    public Card execute(CreateCardDto dto) throws StripeException {
        CardHolder cardHolder = getCardHolderUseCase.execute(dto.getCardHolderId());
        Wallet wallet = getWalletUseCase.execute(dto.getWalletId());
        Account connectAccount = getAccountUseCase.execute(dto.getAccountId());
        User user = getUserUseCase.execute(dto.getUserId());

        com.stripe.model.issuing.Card stripeCard = createStripeCard.execute(cardHolder.getStripeCardHolderId(),
                wallet.getStripeFinancialAccountId(),
                connectAccount.getConnectAccountId());

        Card card = new Card(stripeCard.getId(), cardHolder, wallet, user, stripeCard.getLast4(),
                stripeCard.getStatus(), stripeCard.getBrand(), stripeCard.getType(),
                stripeCard.getCurrency(), stripeCard.getExpYear());

        Card saved = cardRepo.save(card);
        return saved;
    }
}
