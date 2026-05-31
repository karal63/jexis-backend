package com.jexis.jexis_backend.stripe.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jexis.jexis_backend.stripe.application.dto.ConnectResponse;
import com.jexis.jexis_backend.stripe.application.dto.CreateConnectDto;
import com.jexis.jexis_backend.stripe.application.dto.CreateLinkDto;
import com.jexis.jexis_backend.stripe.application.dto.LinkResult;
import com.jexis.jexis_backend.stripe.application.useCases.CreateConnectUseCase;
import com.jexis.jexis_backend.stripe.application.useCases.CreateLinkUseCase;
import com.stripe.exception.StripeException;
import com.stripe.model.v2.core.Account;
import com.stripe.model.v2.core.AccountLink;

@Controller
@RequestMapping("")
public class StripeController {
    private final CreateConnectUseCase createConnectUseCase;
    private final CreateLinkUseCase createLinkUseCase;

    public StripeController(CreateConnectUseCase createConnectUseCase, CreateLinkUseCase createLinkUseCase) {
        this.createConnectUseCase = createConnectUseCase;
        this.createLinkUseCase = createLinkUseCase;
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
}
