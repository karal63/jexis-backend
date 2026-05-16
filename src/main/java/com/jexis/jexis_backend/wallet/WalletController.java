package com.jexis.jexis_backend.wallet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @GetMapping("/list")
    public String list() {
        return "Get all wallets";
    }

    @GetMapping("/list/{id}")
    public String find(@RequestParam String id) {
        return "Get all wallets";
    }

    @PostMapping("/create")
    public String create() {
        return "Create project";
    }

    @PatchMapping("/edit/{id}")
    public String edit(@RequestParam String id) {
        return "Edit project";
    }

    @PostMapping("/delete/{id}")
    public String delete(@RequestParam String id) {
        return "Delete project";
    }
}
