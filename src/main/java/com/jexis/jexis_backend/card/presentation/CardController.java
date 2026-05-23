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

    @GetMapping("/list")
    public List<Card> list() {
        return getAllCardsUseCase.execute();
    }

    @GetMapping("/list/{id}")
    public Card find(@PathVariable UUID id) {
        return getCardUseCase.execute(id);
    }

    @PostMapping("/create")
    public Card create(@RequestBody CreateCardDto body) {
        User user = getUserUseCase.execute(body.getUserId());
        return createCardUseCase.execute(user, body.getLast4(), body.getStatus(), body.getLimit());
    }

    @PatchMapping("/edit/{id}")
    public Card edit(@PathVariable UUID id, @RequestBody EditCardDto body) {
        return editCardUseCase.execute(id, body);
    }

    @PostMapping("/delete/{id}")
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable UUID id) {
        deleteCardUseCase.execute(user, id);
    }
}
