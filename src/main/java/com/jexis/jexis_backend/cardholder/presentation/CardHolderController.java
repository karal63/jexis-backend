package com.jexis.jexis_backend.cardholder.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.auth.application.dto.AuthUser;
import com.jexis.jexis_backend.cardholder.application.dto.CreateCardHolderDto;
import com.jexis.jexis_backend.cardholder.application.dto.EditCardHolderDto;
import com.jexis.jexis_backend.cardholder.application.useCases.CreateCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.DeleteCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.EditCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.GetAllCardHoldersUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.GetCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.stripe.exception.StripeException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/card-holder")
public class CardHolderController {
    private final GetAllCardHoldersUseCase getAllCardHoldersUseCase;
    private final GetCardHolderUseCase getCardHolderUseCase;
    private final CreateCardHolderUseCase createCardHolderUseCase;
    private final EditCardHolderUseCase editCardHolderUseCase;
    private final DeleteCardHolderUseCase deleteCardHolderUseCase;

    public CardHolderController(GetAllCardHoldersUseCase getAllCardHoldersUseCase,
            GetCardHolderUseCase getCardHolderUseCase, CreateCardHolderUseCase createCardHolderUseCase,
            EditCardHolderUseCase editCardHolderUseCase, DeleteCardHolderUseCase deleteCardHolderUseCase) {
        this.getAllCardHoldersUseCase = getAllCardHoldersUseCase;
        this.getCardHolderUseCase = getCardHolderUseCase;
        this.createCardHolderUseCase = createCardHolderUseCase;
        this.editCardHolderUseCase = editCardHolderUseCase;
        this.deleteCardHolderUseCase = deleteCardHolderUseCase;
    }

    @GetMapping("/list")
    public List<CardHolder> list() {
        return getAllCardHoldersUseCase.execute();
    }

    @GetMapping("/list/{id}")
    public CardHolder find(@PathVariable UUID id) {
        return getCardHolderUseCase.execute(id);
    }

    @PostMapping("/create")
    public CardHolder create(@RequestBody CreateCardHolderDto body, HttpServletRequest request) throws StripeException {
        return createCardHolderUseCase.execute(body, request);
    }

    @PatchMapping("/edit/{id}")
    public CardHolder edit(@PathVariable UUID id, @RequestBody EditCardHolderDto body) {
        return editCardHolderUseCase.execute(id, body);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable UUID id) {
        deleteCardHolderUseCase.execute(user, id);
    }
}
