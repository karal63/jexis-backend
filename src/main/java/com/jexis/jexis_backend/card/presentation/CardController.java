package com.jexis.jexis_backend.card.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.card.application.dto.CreateCardDto;
import com.jexis.jexis_backend.card.application.dto.EditCardDto;
import com.jexis.jexis_backend.card.application.useCases.CreateCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.DeleteCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.EditCardUseCase;
import com.jexis.jexis_backend.card.application.useCases.GetAllCardsUseCase;
import com.jexis.jexis_backend.card.application.useCases.GetCardUseCase;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.user.application.useCases.GetUserUseCase;
import com.jexis.jexis_backend.user.domain.entities.User;

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
    private final GetUserUseCase getUserUseCase;

    public CardController(
            GetAllCardsUseCase getAllCardsUseCase,
            GetCardUseCase getCardUseCase,
            CreateCardUseCase createCardUseCase,
            EditCardUseCase editCardUseCase,
            DeleteCardUseCase deleteCardUseCase,
            GetUserUseCase getUserUseCase) {
        this.getAllCardsUseCase = getAllCardsUseCase;
        this.getCardUseCase = getCardUseCase;
        this.createCardUseCase = createCardUseCase;
        this.editCardUseCase = editCardUseCase;
        this.deleteCardUseCase = deleteCardUseCase;
        this.getUserUseCase = getUserUseCase;
    }

    /**
     * Retrieves all cards available in the system.
     *
     * Endpoint: GET /card/list
     *
     * @return a list of all card entities
     */
    @GetMapping("/list")
    public List<Card> list() {
        return getAllCardsUseCase.execute();
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
    public Card find(@PathVariable UUID id) {
        return getCardUseCase.execute(id);
    }

    /**
     * Creates a new card for the specified user.
     *
     * Endpoint: POST /card/create
     *
     * @param body the request payload containing card details and user information
     * @return the newly created card entity
     */
    @PostMapping("/create")
    public Card create(@RequestBody CreateCardDto body) {
        User user = getUserUseCase.execute(body.getUserId());
        return createCardUseCase.execute(user, body.getLast4(), body.getStatus(), body.getLimit(), body.getBrand(),
                body.getType(), body.getCurrency(), body.getExpYear());
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
    @PatchMapping("/edit/{id}")
    public Card edit(@PathVariable UUID id, @RequestBody EditCardDto body) {
        return editCardUseCase.execute(id, body);
    }

    /**
     * Deletes a card owned by the authenticated user.
     *
     * Endpoint: POST /card/delete/{id}
     *
     * @param user the authenticated user making the request
     * @param id   the unique identifier of the card to delete
     */
    @PostMapping("/delete/{id}")
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable UUID id) {
        deleteCardUseCase.execute(user, id);
    }
}
