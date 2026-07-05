package com.jexis.jexis_backend.card.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.card.application.dto.CardResponseDto;
import com.jexis.jexis_backend.card.application.dto.CreateCardDto;
import com.jexis.jexis_backend.card.application.dto.EditCardDto;
import com.jexis.jexis_backend.card.application.useCases.CreateCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.DeleteCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.EditCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.GetAccountCardsUseCase;
import com.jexis.jexis_backend.card.application.useCases.GetAllCardsUseCase;
import com.jexis.jexis_backend.card.application.useCases.GetCardUseCase;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;

import jakarta.validation.Valid;

/**
 * CardController
 * REST controller in the presentation layer responsible for exposing
 * card-related HTTP endpoints.
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated card use case
 * services (application layer).
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 * Author: Leo
 */
@RestController
@RequestMapping("/")
public class CardController {
    private final GetAllCardsUseCase getAllCardsUseCase;
    private final GetCardUseCase getCardUseCase;
    private final CreateCardUseCase createCardUseCase;
    private final EditCardUseCase editCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;
    private final DtoHelper dtoHelper;
    private final GetAccountCardsUseCase getAccountCardsUseCase;

    public CardController(
            GetAllCardsUseCase getAllCardsUseCase,
            GetCardUseCase getCardUseCase,
            CreateCardUseCase createCardUseCase,
            EditCardUseCase editCardUseCase,
            DeleteCardUseCase deleteCardUseCase,
            DtoHelper dtoHelper,
            GetAccountCardsUseCase getAccountCardsUseCase) {
        this.getAllCardsUseCase = getAllCardsUseCase;
        this.getCardUseCase = getCardUseCase;
        this.createCardUseCase = createCardUseCase;
        this.editCardUseCase = editCardUseCase;
        this.deleteCardUseCase = deleteCardUseCase;
        this.dtoHelper = dtoHelper;
        this.getAccountCardsUseCase = getAccountCardsUseCase;
    }

    /**
     * Retrieves all cards available in the account.
     * Endpoint: GET /card/list
     *
     * @return a list of all card entities
     */
    @PreAuthorize("@userAuthorization.isAdmin(authentication.principal.roles())")
    @GetMapping("/admin/cards")
    public List<CardResponseDto> list() {
        List<Card> cards = getAllCardsUseCase.execute();
        return cards.stream().map(dtoHelper::toCardDto).toList();
    }

    /**
     * Creates a new card for the specified cardholder.
     * Endpoint: POST /card/create
     *
     * @param body the request payload containing card details and cardholder
     *             information
     * @return the newly created card entity
     */
    @PostMapping("/cards/create")
    @PreAuthorize("@cardAuthorization.canCreate(authentication.principal.id(), #body.accountId)")
    public CardResponseDto create(@Valid @RequestBody CreateCardDto body) {
        Card card = createCardUseCase.execute(body);
        return dtoHelper.toCardDto(card);
    }

    @GetMapping("/accounts/{id}/cards")
    @PreAuthorize("@cardAuthorization.canViewAll(authentication.principal.id(), #id)")
    public List<CardResponseDto> getCardsByAccount(@PathVariable UUID id) {
        List<Card> card = getAccountCardsUseCase.execute(id);
        return card.stream().map(dtoHelper::toCardDto).toList();
    }

    /**
     * Retrieves a single card by its identifier.
     *
     * Endpoint: GET /card/list/{id}
     *
     * @param id the unique identifier of the card to retrieve
     * @return the matching card entity
     */
    @GetMapping("/accounts/{id}/cards/{cardId}")
    @PreAuthorize("@cardAuthorization.canView(authentication.principal.id(), #id, #cardId)")
    public CardResponseDto find(@PathVariable UUID id, @PathVariable UUID cardId) {
        Card card = getCardUseCase.execute(cardId);
        return dtoHelper.toCardDto(card);
    }

    /**
     * Updates an existing card with the provided changes.
     *
     * Endpoint: PATCH /card/edit/{id}
     *
     * @param id   the unique identifier of the card to update
     * @param body the card update payload
     * @return the updated card entity
     */
    @PatchMapping("/accounts/{id}/cards/{cardId}/edit")
    @PreAuthorize("@cardAuthorization.canEdit(authentication.principal.id(), #id, #cardId)")
    public CardResponseDto edit(@PathVariable UUID id, @PathVariable UUID cardId, @RequestBody EditCardDto body) {
        Card card = editCardUseCase.execute(cardId, body);
        return dtoHelper.toCardDto(card);
    }

    /**
     * Deletes a card owned by the authenticated user.
     *
     * Endpoint: POST /card/delete/{id}
     *
     * @param id account id
     * @param cardId card id
     */
    @PostMapping("/accounts/{id}/cards/{cardId}/delete")
    @PreAuthorize("@cardAuthorization.canDelete(authentication.principal.id(), #id, #cardId)")
    public void delete(@PathVariable UUID id, @PathVariable UUID cardId) {
        deleteCardUseCase.execute(cardId);
    }
}
