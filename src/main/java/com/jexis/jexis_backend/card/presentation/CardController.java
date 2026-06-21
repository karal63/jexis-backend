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
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.card.application.dto.CardResponseDto;
import com.jexis.jexis_backend.card.application.dto.CreateCardDto;
import com.jexis.jexis_backend.card.application.dto.EditCardDto;
import com.jexis.jexis_backend.card.application.useCases.CreateCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.DeleteCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.EditCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.GetAllCardsUseCase;
import com.jexis.jexis_backend.card.application.useCases.GetCardUseCase;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;

import jakarta.validation.Valid;

/**
 * CardController
 *
 * REST controller in the presentation layer responsible for exposing
 * card-related HTTP endpoints.
 *
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated card use case
 * services (application layer).
 *
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 *
 * Author: Leo
 */
@RestController
@RequestMapping("/card")
public class CardController {
    private final GetAllCardsUseCase getAllCardsUseCase;
    private final GetCardUseCase getCardUseCase;
    private final CreateCardUseCase createCardUseCase;
    private final EditCardUseCase editCardUseCase;
    private final DeleteCardUseCase deleteCardUseCase;
    private final DtoHelper dtoHelper;

    public CardController(
            GetAllCardsUseCase getAllCardsUseCase,
            GetCardUseCase getCardUseCase,
            CreateCardUseCase createCardUseCase,
            EditCardUseCase editCardUseCase,
            DeleteCardUseCase deleteCardUseCase,
            DtoHelper dtoHelper) {
        this.getAllCardsUseCase = getAllCardsUseCase;
        this.getCardUseCase = getCardUseCase;
        this.createCardUseCase = createCardUseCase;
        this.editCardUseCase = editCardUseCase;
        this.deleteCardUseCase = deleteCardUseCase;
        this.dtoHelper = dtoHelper;
    }

    /**
     * Retrieves all cards available in the account.
     *
     * Endpoint: GET /card/list/{accountId}
     *
     * @return a list of all card entities
     */
    // Role (i would say only admin and owner has access)
    @GetMapping("/list/{accountId}")
    @PreAuthorize("@canAccessUseCase.execute(authentication.principal.id(), #accountId)")
    public List<CardResponseDto> list(@PathVariable UUID accountId) {
        List<Card> cards = getAllCardsUseCase.execute(accountId);
        return cards.stream().map(dtoHelper::toCardDto).toList();
    }

    /**
     * Retrieves a single card by its identifier.
     *
     * Endpoint: GET /card/list/{id}
     *
     * @param id the unique identifier of the card to retrieve
     * @return the matching card entity
     */
    @GetMapping("/list/{id}")
    @PreAuthorize("@canAccessUseCase.execute(authentication.principal.id(), #accountId)")
    public CardResponseDto find(@PathVariable UUID accountId, @PathVariable UUID id) {
        Card card = getCardUseCase.execute(accountId, id);
        return dtoHelper.toCardDto(card);
    }

    /**
     * Creates a new card for the specified card holder.
     *
     * Endpoint: POST /card/create
     *
     * @param body the request payload containing card details and card holder
     *             information
     * @return the newly created card entity
     */
    // Role (i would say only admin and owner has access)
    @PostMapping("/create")
    public CardResponseDto create(@Valid @RequestBody CreateCardDto body) {
        Card card = createCardUseCase.execute(body);
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
    // Role (i would say only admin and owner has access)
    @PatchMapping("/edit/{id}")
    public CardResponseDto edit(@PathVariable UUID id, @RequestBody EditCardDto body) {
        Card card = editCardUseCase.execute(id, body);
        return dtoHelper.toCardDto(card);
    }

    /**
     * Deletes a card owned by the authenticated user.
     *
     * Endpoint: POST /card/delete/{id}
     *
     * @param user the authenticated user making the request
     * @param id   the unique identifier of the card to delete
     */
    // Role (i would say only admin and owner has access)
    @PostMapping("/delete/{id}")
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable UUID id) {
        deleteCardUseCase.execute(user, id);
    }
}
