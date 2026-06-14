package com.jexis.jexis_backend.cardholder.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.cardholder.infrastructure.CardHolderRepository;

@Service
public class GetAllCardHoldersUseCase {
    private final CardHolderRepository repo;

    public GetAllCardHoldersUseCase(CardHolderRepository repo) {
        this.repo = repo;
    }

    public List<CardHolder> execute() {
        return repo.findAll();
    }
}
