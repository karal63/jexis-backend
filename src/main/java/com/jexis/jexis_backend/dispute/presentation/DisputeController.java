package com.jexis.jexis_backend.dispute.presentation;

import com.jexis.jexis_backend.dispute.application.dto.CreateDisputeDto;
import com.jexis.jexis_backend.dispute.application.useCases.CreateDisputeUseCase;
import com.jexis.jexis_backend.dispute.domain.entities.Dispute;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DisputeController {
    private final CreateDisputeUseCase createDisputeUseCase;

    @PostMapping("/dispute/create")
    public void create(@Valid @RequestBody CreateDisputeDto body) {
        createDisputeUseCase.execute(body);
    }
}

