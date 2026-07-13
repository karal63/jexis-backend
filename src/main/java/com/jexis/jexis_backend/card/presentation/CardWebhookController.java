package com.jexis.jexis_backend.card.presentation;

import com.jexis.jexis_backend.common.logging.AsyncLogger;
import com.jexis.jexis_backend.transaction.application.dto.CreateCardTransactionDto;
import com.jexis.jexis_backend.transaction.application.useCases.CreateCardTransactionUseCase;
import com.jexis.jexis_backend.transaction.domain.enums.TransactionDirection;
import com.jexis.jexis_backend.transaction.domain.enums.TransactionStatus;
import com.jexis.jexis_backend.transaction.domain.enums.TransactionType;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.issuing.Transaction;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhooks/cards")
public class CardWebhookController {
    @Value("${stripe.webhook.secret.card}")
    private String webhookSecret;
    private final AsyncLogger logger;
    private final CreateCardTransactionUseCase createCardTransactionUseCase;


    public CardWebhookController(AsyncLogger logger, CreateCardTransactionUseCase createCardTransactionUseCase) {
        this.logger = logger;
        this.createCardTransactionUseCase = createCardTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, this.webhookSecret);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("invalid signature");
        }

        switch (event.getType()) {
            case "issuing_transaction.created":
                StripeObject object = event.getDataObjectDeserializer()
                        .getObject().orElseThrow(() -> new IllegalStateException("Unable to deserialize object"));

                Transaction issuingTransaction =
                        (Transaction) object;

                CreateCardTransactionDto dto = new CreateCardTransactionDto(
                        event.getAccount(),
                        issuingTransaction.getTreasury().getReceivedDebit(),
                        issuingTransaction.getId(),
                        TransactionType.CARD_TRANSACTION,
                        issuingTransaction.getAmount(),
                        issuingTransaction.getCurrency(),
                        TransactionStatus.COMPLETED,
                        TransactionDirection.DEBIT,
                        issuingTransaction.getCard(),
                        issuingTransaction.getMerchantData().getName(),
                        issuingTransaction.getMerchantData().getCategory(),
                        issuingTransaction.getMerchantData().getCity(),
                        issuingTransaction.getMerchantData().getCountry()
                );
                createCardTransactionUseCase.execute(dto);
                logger.info("STRIPE", "Issuing transaction created");

        }
        return ResponseEntity.ok("success");
    }
}
