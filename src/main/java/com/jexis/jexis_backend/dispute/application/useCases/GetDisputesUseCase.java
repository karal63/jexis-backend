package com.jexis.jexis_backend.dispute.application.useCases;

import com.jexis.jexis_backend.dispute.domain.entities.Dispute;
import com.jexis.jexis_backend.dispute.infrastructure.DisputeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDisputesUseCase {
    private final DisputeRepository disputeRepository;

    public GetDisputesUseCase(DisputeRepository disputeRepository) {
        this.disputeRepository = disputeRepository;
    }

    public List<Dispute> execute() {
        return disputeRepository.findAll();
    }
}
