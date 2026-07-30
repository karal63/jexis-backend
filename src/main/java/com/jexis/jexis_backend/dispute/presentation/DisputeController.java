package com.jexis.jexis_backend.dispute.presentation;

import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.dispute.application.dto.CreateDisputeDto;
import com.jexis.jexis_backend.dispute.application.dto.DisputeResponseDto;
import com.jexis.jexis_backend.dispute.application.useCases.CreateDisputeUseCase;
import com.jexis.jexis_backend.dispute.application.useCases.GetCardDisputesUseCase;
import com.jexis.jexis_backend.dispute.application.useCases.GetDisputeUseCase;
import com.jexis.jexis_backend.dispute.application.useCases.GetDisputesUseCase;
import com.jexis.jexis_backend.dispute.domain.entities.Dispute;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/")
@RestController
@RequiredArgsConstructor
public class DisputeController {
    private final CreateDisputeUseCase createDisputeUseCase;
    private final GetDisputesUseCase getDisputesUseCase;
    private final GetCardDisputesUseCase getCardDisputesUseCase;
    private final GetDisputeUseCase getDisputeUseCase;
    private final DtoHelper dtoHelper;

    @PostMapping("/dispute/create")
    @PreAuthorize("@disputeAuthorization.canCreateDispute(authentication.principal.id(), #body.transactionId())")
    public Dispute create(@Valid @RequestBody CreateDisputeDto body) {
        return createDisputeUseCase.execute(body);
    }

    @GetMapping("/admin/disputes")
    @PreAuthorize("@disputeAuthorization.canViewAll(authentication.principal.id())")
    public List<DisputeResponseDto> getAllDisputes() {
        return getDisputesUseCase.execute().stream()
                .map(dtoHelper::toDisputeDto)
                .toList();
    }

    @GetMapping("/cards/{cardId}/disputes")
    @PreAuthorize("@disputeAuthorization.canViewCardDisputes(authentication.principal.id(), #cardId)")
    public List<DisputeResponseDto> getCardDisputes(@PathVariable UUID cardId) {
        return getCardDisputesUseCase.execute(cardId).stream()
                .map(dtoHelper::toDisputeDto)
                .toList();
    }

    @GetMapping("/disputes/{id}")
    @PreAuthorize("@disputeAuthorization.canView(authentication.principal.id(), #id)")
    public DisputeResponseDto getDispute(@PathVariable UUID id) {
        Dispute dispute = getDisputeUseCase.execute(id);
        return dtoHelper.toDisputeDto(dispute);
    }
}
