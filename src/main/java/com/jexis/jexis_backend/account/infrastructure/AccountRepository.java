package com.jexis.jexis_backend.account.infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jexis.jexis_backend.account.domain.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {}