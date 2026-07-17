package com.jexis.jexis_backend.wallet.application.useCases;

import com.jexis.jexis_backend.stripe.application.useCases.StripeOutboundTransferUseCase;
import com.jexis.jexis_backend.wallet.application.dto.CreateOutboundTransferDto;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateOutboundTransferUseCase {
    private final StripeOutboundTransferUseCase stripeOutboundTransferUseCase;
    private final GetWalletUseCase getWalletUseCase;

    public CreateOutboundTransferUseCase(StripeOutboundTransferUseCase stripeOutboundTransferUseCase, GetWalletUseCase getWalletUseCase) {
        this.stripeOutboundTransferUseCase = stripeOutboundTransferUseCase;
        this.getWalletUseCase = getWalletUseCase;
    }

    public void execute(UUID walletId, CreateOutboundTransferDto body) {
        Wallet wallet = getWalletUseCase.execute(walletId);
        stripeOutboundTransferUseCase.execute(wallet.getAccount().getConnectAccountId(), wallet.getStripeFinancialAccountId(), body.externalAccountId(), body);
    }
}
