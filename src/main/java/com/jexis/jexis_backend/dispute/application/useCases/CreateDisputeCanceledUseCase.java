package com.jexis.jexis_backend.dispute.application.useCases;

import com.jexis.jexis_backend.dispute.application.dto.CreateDisputeDto;
import com.jexis.jexis_backend.dispute.application.dto.ProductType;
import com.jexis.jexis_backend.dispute.application.dto.ReturnStatus;
import com.jexis.jexis_backend.dispute.application.dto.canceled.CanceledEvidenceDto;
import com.jexis.jexis_backend.dispute.domain.entities.Dispute;
import com.stripe.param.issuing.DisputeCreateParams;
import org.springframework.stereotype.Service;

@Service
public class CreateDisputeCanceledUseCase {
    public void execute(CreateDisputeDto body, DisputeCreateParams.Evidence.Builder params) {
        CanceledEvidenceDto evidence = body.disputeEvidence().canceled();

        params.setCanceled(
                DisputeCreateParams.Evidence.Canceled.builder()
                        .setProductType(mapProductType(evidence.productType()))
                        .setProductDescription(evidence.productDescription())
                        .setExpectedAt(evidence.expectedAt())
                        .setReturnStatus(mapReturnStatus(evidence.returnStatus()))
                        .setReturnedAt(evidence.returnedAt())
                        .setCancellationPolicyProvided(evidence.cancellationPolicyProvided())
                        .setCanceledAt(evidence.canceledAt())
                        .setCancellationReason(evidence.cancellationReason())
                        .setExplanation(!evidence.explanation().isEmpty() ? evidence.explanation() : null)
                        .build()
        );
    }

    private DisputeCreateParams.Evidence.Canceled.ProductType mapProductType(ProductType productType) {
        return switch (productType) {
            case merchandise -> DisputeCreateParams.Evidence.Canceled.ProductType.MERCHANDISE;
            case service ->  DisputeCreateParams.Evidence.Canceled.ProductType.SERVICE;
        };
    }

    private DisputeCreateParams.Evidence.Canceled.ReturnStatus mapReturnStatus(ReturnStatus returnStatus) {
        return switch (returnStatus) {
            case merchant_rejected -> DisputeCreateParams.Evidence.Canceled.ReturnStatus.MERCHANT_REJECTED;
            case successful ->  DisputeCreateParams.Evidence.Canceled.ReturnStatus.SUCCESSFUL;
        };
    }
}
