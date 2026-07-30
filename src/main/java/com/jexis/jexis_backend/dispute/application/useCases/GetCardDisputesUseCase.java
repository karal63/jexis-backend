package com.jexis.jexis_backend.dispute.application.useCases;

import com.jexis.jexis_backend.dispute.domain.entities.Dispute;
import com.jexis.jexis_backend.dispute.infrastructure.DisputeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetCardDisputesUseCase {
    private final DisputeRepository disputeRepository;

    public GetCardDisputesUseCase(DisputeRepository disputeRepository) {
        this.disputeRepository = disputeRepository;
    }

    public List<Dispute> execute(UUID cardId) {
        return disputeRepository.findAllByTransaction_Card_Id(cardId);
    }
}
