package com.jexis.jexis_backend.cardholder.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
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
import com.jexis.jexis_backend.cardholder.application.dto.CardHolderResponseDto;
import com.jexis.jexis_backend.cardholder.application.dto.CreateCardHolderDto;
import com.jexis.jexis_backend.cardholder.application.dto.EditCardHolderDto;
import com.jexis.jexis_backend.cardholder.application.useCases.CreateCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.DeleteCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.EditCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.GetAccountCardHoldersUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.GetAllCardHoldersUseCase;
import com.jexis.jexis_backend.cardholder.application.useCases.GetCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class CardHolderController {
    private final GetAllCardHoldersUseCase getAllCardHoldersUseCase;
    private final GetCardHolderUseCase getCardHolderUseCase;
    private final CreateCardHolderUseCase createCardHolderUseCase;
    private final EditCardHolderUseCase editCardHolderUseCase;
    private final DeleteCardHolderUseCase deleteCardHolderUseCase;
    private final DtoHelper dtoHelper;
    private final GetAccountCardHoldersUseCase getAccountCardHoldersUseCase;

    public CardHolderController(GetAllCardHoldersUseCase getAllCardHoldersUseCase,
            GetCardHolderUseCase getCardHolderUseCase, CreateCardHolderUseCase createCardHolderUseCase,
            EditCardHolderUseCase editCardHolderUseCase, DeleteCardHolderUseCase deleteCardHolderUseCase,
            DtoHelper dtoHelper, GetAccountCardHoldersUseCase getAccountCardHoldersUseCase) {
        this.getAllCardHoldersUseCase = getAllCardHoldersUseCase;
        this.getCardHolderUseCase = getCardHolderUseCase;
        this.createCardHolderUseCase = createCardHolderUseCase;
        this.editCardHolderUseCase = editCardHolderUseCase;
        this.deleteCardHolderUseCase = deleteCardHolderUseCase;
        this.dtoHelper = dtoHelper;
        this.getAccountCardHoldersUseCase = getAccountCardHoldersUseCase;
    }

    // Global Admin
    @GetMapping("/list")
    public List<CardHolderResponseDto> list() {
        List<CardHolder> cardHolders = getAllCardHoldersUseCase.execute();
        return cardHolders.stream().map(dtoHelper::toCardHolderDto).toList();
    }

    @PostMapping("/card-holder/create")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #body.accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #body.accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public CardHolderResponseDto create(@RequestBody CreateCardHolderDto body, HttpServletRequest request) {
        CardHolder cardHolder = createCardHolderUseCase.execute(body, request);
        return dtoHelper.toCardHolderDto(cardHolder);
    }

    @GetMapping("/accounts/{accountId}/card-holders")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public List<CardHolderResponseDto> getCardHoldersByAccount(@PathVariable UUID id) {
        List<CardHolder> cardHolder = getAccountCardHoldersUseCase.execute(id);
        return cardHolder.stream().map(dtoHelper::toCardHolderDto).toList();
    }

    @GetMapping("/accounts/{id}/card-holders/{cardHolderId}")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public CardHolderResponseDto find(@PathVariable UUID cardHolderId) {
        CardHolder cardHolder = getCardHolderUseCase.execute(cardHolderId);
        return dtoHelper.toCardHolderDto(cardHolder);
    }

    @PatchMapping("/accounts/{id}/card-holders/{cardHolderId}/edit")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public CardHolderResponseDto edit(@PathVariable UUID cardHolderId, @RequestBody EditCardHolderDto body) {
        CardHolder cardHolder = editCardHolderUseCase.execute(cardHolderId, body);
        return dtoHelper.toCardHolderDto(cardHolder);
    }

    @PostMapping("/accounts/{id}/card-holders/{cardHolderId}/remove")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #id, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public void delete(@AuthenticationPrincipal AuthUser user, @PathVariable UUID cardHolderId) {
        deleteCardHolderUseCase.execute(user, cardHolderId);
    }
}
