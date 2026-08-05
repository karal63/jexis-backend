package com.jexis.jexis_backend.authorization.infrastructure;

import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import com.jexis.jexis_backend.authorization.domain.enums.AuthorizationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorizationRepository extends JpaRepository<Authorization, UUID> {
    List<Authorization> findByWalletId(UUID walletId);
    Optional<Authorization> findByStripeAuthorizationId(String stripeAuthorizationId);

    @Query("""
        SELECT a FROM Authorization a 
            WHERE 
                (lower(a.merchantCategory) like lower(concat('%', :search, '%')) 
                    OR lower(a.merchantName) like lower(concat('%', :search, '%'))
                    OR lower(a.merchantCity) like lower(concat('%', :search, '%'))
                    OR lower(a.merchantCountry) like lower(concat('%', :search, '%')))
                    AND coalesce(a.approved, false) = :approved
                    AND (:status IS NULL OR a.status = :status)
    """)
    Page<Authorization> findAuthorizationsWithFilters(
            Pageable pageable,
            @Param("search") String search,
            @Param("approved") Boolean approved,
            @Param("status") AuthorizationStatus status);

    @Query("""
        SELECT a FROM Authorization a 
            WHERE 
                (lower(a.merchantCategory) like lower(concat('%', :search, '%')) 
                    OR lower(a.merchantName) like lower(concat('%', :search, '%'))
                    OR lower(a.merchantCity) like lower(concat('%', :search, '%'))
                    OR lower(a.merchantCountry) like lower(concat('%', :search, '%')))
                    AND coalesce(a.approved, false) = :approved
                    AND (:status IS NULL OR a.status = :status)
    """)
    Page<Authorization> findWalletAuthorizationsWithFilters(
            @Param("walletId") UUID walletId,
            Pageable pageable,
            @Param("search") String search,
            @Param("approved") Boolean approved,
            @Param("status") AuthorizationStatus status);
}
