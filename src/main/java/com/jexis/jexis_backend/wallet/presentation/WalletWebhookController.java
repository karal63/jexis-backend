package com.jexis.jexis_backend.wallet.presentation;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.transaction.application.dto.CreateBankTransactionDto;
import com.jexis.jexis_backend.transaction.application.useCases.CreateBankTransactionUseCase;
import com.jexis.jexis_backend.transaction.domain.enums.PaymentMethod;
import com.jexis.jexis_backend.transaction.domain.enums.TransactionDirection;
import com.jexis.jexis_backend.transaction.domain.enums.TransactionStatus;
import com.jexis.jexis_backend.transaction.domain.enums.TransactionType;
import com.stripe.exception.EventDataObjectDeserializationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.treasury.ReceivedCredit;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/webhooks/wallets")
public class WalletWebhookController {
    @Value("${stripe.webhook.secret.wallet}")
    private String webhookSecret;
    private final AsyncLogger logger;
    private final CreateBankTransactionUseCase createBankTransactionUseCase;

    public WalletWebhookController(AsyncLogger logger, CreateBankTransactionUseCase createBankTransactionUseCase) {
        this.logger = logger;
        this.createBankTransactionUseCase = createBankTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        switch (event.getType()) {
            case "treasury.received_credit.created":
                logger.info("STRIPE_WEBHOOK", "Webhook received credit created");

                ReceivedCredit credit =
                        (ReceivedCredit) event.getDataObjectDeserializer()
                                .getObject().orElseThrow(() -> new IllegalStateException("Received Credit object is null"));

                CreateBankTransactionDto dto = new CreateBankTransactionDto(
                        event.getAccount(),
                        credit.getTransaction(),
                        credit.getFinancialAccount(),
                        credit.getId(),
                        TransactionType.INBOUND_TRANSFER,
                        credit.getAmount(),
                        credit.getCurrency(),
                        TransactionStatus.COMPLETED,
                        TransactionDirection.CREDIT,
                        credit.getInitiatingPaymentMethodDetails().getUsBankAccount().getBankName(),
                        credit.getInitiatingPaymentMethodDetails().getUsBankAccount().getLast4(),
                        credit.getInitiatingPaymentMethodDetails().getUsBankAccount().getRoutingNumber(),
                        PaymentMethod.valueOf(credit.getNetwork().toUpperCase())
                );

                createBankTransactionUseCase.execute(dto);
                logger.info("STRIPE_WEBHOOK", "Received treasury credit created");
                break;
        }

        return ResponseEntity.ok("success");
    }
}
