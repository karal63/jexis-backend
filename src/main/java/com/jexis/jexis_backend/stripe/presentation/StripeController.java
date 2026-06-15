package com.jexis.jexis_backend.stripe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jexis.jexis_backend.cardholder.application.useCases.CreateCardHolderUseCase;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.stripe.application.dto.CreateStripeHolderDto;
import com.jexis.jexis_backend.stripe.application.dto.CreateTreasuryAccountDto;
import com.jexis.jexis_backend.stripe.application.dto.EnableIssuingDto;
import com.jexis.jexis_backend.stripe.application.useCases.CreateTreasuryAccount;
import com.jexis.jexis_backend.stripe.application.useCases.SetupIssuingUseCase;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.StripeCollection;
import com.stripe.model.treasury.FinancialAccount;
import com.stripe.net.RequestOptions;
import com.stripe.param.AccountListParams;
import com.stripe.param.treasury.FinancialAccountUpdateParams;

/**
 * StripeController
 *
 * REST controller in the presentation layer responsible for exposing
 * stripe-related HTTP endpoints.
 *
 * It handles request routing, input validation, and response mapping,
 * delegating all business logic execution to dedicated stripe use case
 * services (application layer).
 *
 * This class does not contain domain logic; its role is limited to
 * orchestrating request/response flow between the client and the
 * application layer.
 *
 * Author: Leo
 */
@Controller
@RequestMapping("")
public class StripeController {

    private final SetupIssuingUseCase setupIssuingUseCase;
    private final StripeClient stripe;
    private final CreateCardHolderUseCase createCardHolderUseCase;
    private final CreateTreasuryAccount createTreasuryAccount;

    public StripeController(SetupIssuingUseCase setupIssuingUseCase, StripeClient stripe,
            CreateCardHolderUseCase createCardHolderUseCase, CreateTreasuryAccount createTreasuryAccount) {
        this.setupIssuingUseCase = setupIssuingUseCase;
        this.stripe = stripe;
        this.createCardHolderUseCase = createCardHolderUseCase;
        this.createTreasuryAccount = createTreasuryAccount;
    }

    @PostMapping("/enable-issuing")
    public ResponseEntity<String> enableIssuing() throws StripeException {
        try {
            FinancialAccountUpdateParams params = FinancialAccountUpdateParams.builder()
                    .putExtraParam("treasury[access][requested]", true)
                    .putExtraParam("card_issuing[access][requested]", true)
                    .build();

            RequestOptions requestOptions = RequestOptions.builder().setStripeAccount("acct_1TiUKRQ8mP9HhEPG")
                    .build();
            // For SDK versions 29.4.0 or lower, remove '.v1()' from the following line.

            FinancialAccount financialAccount = stripe.v1().treasury().financialAccounts().update(
                    "acct_1TafqCLGRmU4CddW",
                    params,
                    requestOptions);

            // setupIssuingUseCase.execute(body.connectAccountId());
            return ResponseEntity.ok("Issuing enabled");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error enabling issuing: " + e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() throws StripeException {
        AccountListParams params = AccountListParams.builder().build();

        StripeCollection<Account> accounts = stripe.v1().accounts().list(params);

        return ResponseEntity.ok(accounts.toString());
    }

    // @PostMapping("/create-card-holder")
    // public ResponseEntity<String> createCardHolder(@RequestBody
    // CreateStripeHolderDto body) throws StripeException {
    // createCardHolderUseCase.execute(body);
    // return ResponseEntity.ok("Card holder created");
    // }

    // @PostMapping("/create-treasury-account")
    // public ResponseEntity<String> createTreasuryAccount(@RequestBody
    // CreateTreasuryAccountDto body)
    // throws StripeException {
    // try {
    // createTreasuryAccount.execute(body.connectAccountId());
    // return ResponseEntity.ok("Treasury account created");
    // } catch (Exception e) {
    // return ResponseEntity.status(500).body("Error creating treasury account: " +
    // e.getMessage());
    // }
    // }
}
