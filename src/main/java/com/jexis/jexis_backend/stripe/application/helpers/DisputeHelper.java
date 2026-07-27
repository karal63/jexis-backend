package com.jexis.jexis_backend.stripe.application.helpers;

import com.jexis.jexis_backend.dispute.application.dto.ProductType;
import com.jexis.jexis_backend.dispute.application.dto.ReturnStatus;
import com.stripe.param.issuing.DisputeCreateParams;
import org.springframework.stereotype.Service;

@Service
public class DisputeHelper {
    public DisputeCreateParams.Evidence.Canceled.ProductType mapProductType(ProductType productType) {
        return switch (productType) {
            case merchandise -> DisputeCreateParams.Evidence.Canceled.ProductType.MERCHANDISE;
            case service ->  DisputeCreateParams.Evidence.Canceled.ProductType.SERVICE;
        };
    }

    public DisputeCreateParams.Evidence.Canceled.ReturnStatus mapReturnStatus(ReturnStatus returnStatus) {
        return switch (returnStatus) {
            case merchant_rejected -> DisputeCreateParams.Evidence.Canceled.ReturnStatus.MERCHANT_REJECTED;
            case successful ->  DisputeCreateParams.Evidence.Canceled.ReturnStatus.SUCCESSFUL;
        };
    }
}
