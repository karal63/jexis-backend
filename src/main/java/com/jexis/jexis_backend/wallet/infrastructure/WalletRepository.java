package com.jexis.jexis_backend.wallet.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

}
