package com.jexis.jexis_backend.card.application.useCases;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.card.application.dto.ReplaceCardDto;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.card.domain.enums.CardStatus;
import com.jexis.jexis_backend.card.infrastructure.CardRepository;
import com.jexis.jexis_backend.stripe.application.useCases.CreateStripeCardUseCase;

@Service
public class ReplaceCardUseCase {
    private final CardRepository cardRepository;
    private final GetCardUseCase getCardUseCase;
    private final CreateStripeCardUseCase createStripeCardUseCase;

    public ReplaceCardUseCase(CardRepository cardRepository, GetCardUseCase getCardUseCase,
            CreateStripeCardUseCase createStripeCardUseCase) {
        this.cardRepository = cardRepository;
        this.getCardUseCase = getCardUseCase;
        this.createStripeCardUseCase = createStripeCardUseCase;
    }

    public Card execute(UUID cardId, ReplaceCardDto dto) {
        Card card = getCardUseCase.execute(cardId);

        com.stripe.model.issuing.Card stripeCard = createStripeCardUseCase.execute(
                card.getCardHolder().getStripeCardHolderId(),
                card.getTreasuryAccount().getStripeFinancialAccountId(),
                card.getCardHolder().getAccount().getConnectAccountId(),
                card.getStripeCardId(),
                dto.replacementReason());

        Card replacement = new Card(
                stripeCard.getId(),
                card.getCardHolder(),
                card.getTreasuryAccount(),
                card.getUser(),
                stripeCard.getLast4(),
                CardStatus.valueOf(stripeCard.getStatus()),
                stripeCard.getBrand(),
                stripeCard.getType(),
                stripeCard.getCurrency(),
                stripeCard.getExpYear());

        replacement.setSpendingLimits(card.getSpendingLimits());

        return cardRepository.save(replacement);
    }
}
