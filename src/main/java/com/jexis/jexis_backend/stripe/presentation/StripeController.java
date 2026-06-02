package com.jexis.jexis_backend.stripe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jexis.jexis_backend.stripe.application.dto.ConnectResponse;
import com.jexis.jexis_backend.stripe.application.dto.CreateCardHolderDto;
import com.jexis.jexis_backend.stripe.application.dto.CreateConnectDto;
import com.jexis.jexis_backend.stripe.application.dto.CreateLinkDto;
import com.jexis.jexis_backend.stripe.application.dto.LinkResult;
import com.jexis.jexis_backend.stripe.application.useCases.CreateCardHolderUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.CreateConnectUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.CreateLinkUseCase;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.issuing.Cardholder;
import com.stripe.model.v2.core.Account;
import com.stripe.model.v2.core.AccountLink;
import com.stripe.net.RequestOptions;

@Controller
@RequestMapping("")
public class StripeController {
    private final CreateConnectUseCase createConnectUseCase;
    private final CreateLinkUseCase createLinkUseCase;
    private final CreateCardHolderUseCase createCardHolderUseCase;
    private final StripeClient stripe;

    public StripeController(CreateConnectUseCase createConnectUseCase, CreateLinkUseCase createLinkUseCase,
            CreateCardHolderUseCase createCardHolderUseCase, StripeClient stripe) {
        this.createConnectUseCase = createConnectUseCase;
        this.createLinkUseCase = createLinkUseCase;
        this.createCardHolderUseCase = createCardHolderUseCase;
        this.stripe = stripe;

    }

    @PostMapping("/create-connect-account")
    public ResponseEntity<ConnectResponse> createConnectAccount(@RequestBody CreateConnectDto body)
            throws StripeException {
        String email = body.email();

        Account account = createConnectUseCase.execute(email);
        return ResponseEntity.ok(new ConnectResponse(account.getId()));
    }

    @PostMapping("/create-account-link")
    public ResponseEntity<LinkResult> createAccountLink(@RequestBody CreateLinkDto body) throws StripeException {
        String accountId = body.accountId();

        AccountLink accountLink = createLinkUseCase.execute(accountId);
        return ResponseEntity.ok(new LinkResult(accountLink.getUrl()));
    }

    @PostMapping("/create-card-holder")
    public ResponseEntity<String> createCardHolder(@RequestBody CreateCardHolderDto body) throws StripeException {
        Cardholder cardHolder = createCardHolderUseCase.execute(body.connectedAccountId());
        return ResponseEntity.ok(cardHolder.getId());
    }

    @GetMapping("/get-type")
    public ResponseEntity<String> getType() throws StripeException {

        RequestOptions options = RequestOptions.builder()
                .setStripeContext("acct_1TcrbwLGRm3b2S1B")
                .build();

        Account account = stripe.v2().core().accounts().retrieve("acct_1TcrbwLGRm3b2S1B", options);

        return ResponseEntity.ok(account.getDashboard().toString());
    }
}
