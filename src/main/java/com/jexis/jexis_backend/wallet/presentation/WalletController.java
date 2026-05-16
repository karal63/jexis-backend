package com.jexis.jexis_backend.wallet.presentation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.account.application.useCases.GetAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.wallet.application.dto.CreateWalletDto;
import com.jexis.jexis_backend.wallet.application.useCases.CreateWalletUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.GetAllWalletsUseCase;
import com.jexis.jexis_backend.wallet.application.useCases.GetWalletUseCase;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    GetAllWalletsUseCase getAllWalletsUseCase;
    GetAccountUseCase getAccountUseCase;
    CreateWalletUseCase createWalletUseCase;
    GetWalletUseCase getWalletUseCase;

    public WalletController(GetAllWalletsUseCase getAllWalletsUseCase, GetAccountUseCase getAccountUseCase,
            CreateWalletUseCase createWalletUseCase, GetWalletUseCase getWalletUseCase) {
        this.getAllWalletsUseCase = getAllWalletsUseCase;
        this.getAccountUseCase = getAccountUseCase;
        this.createWalletUseCase = createWalletUseCase;
        this.getWalletUseCase = getWalletUseCase;
    }

    @GetMapping("/list")
    public List<Wallet> list() {
        return getAllWalletsUseCase.execute();
    }

    @GetMapping("/list/{id}")
    public Wallet find(@PathVariable UUID id) {
        return getWalletUseCase.execute(id);
    }

    @PostMapping("/create")
    public Wallet create(@RequestBody CreateWalletDto body) {
        Account account = getAccountUseCase.execute(body.getAccountId());
        return createWalletUseCase.execute(account);
    }

    @PatchMapping("/edit/{id}")
    public String edit(@PathVariable String id) {
        return "Edit project";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        return "Delete project";
    }
}
