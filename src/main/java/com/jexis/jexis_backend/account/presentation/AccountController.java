package com.jexis.jexis_backend.account.presentation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.account.application.dto.CreateAccountDto;
import com.jexis.jexis_backend.account.application.useCases.CreateAccountUseCase;
import com.jexis.jexis_backend.account.domain.entities.Account;
import org.springframework.web.bind.annotation.RequestBody;

/**
* Name: AccountController
* Description: This class is responsible for handling HTTP requests related to account management,
  such as creating, deleting, and editing accounts. It uses the CreateAccountUseCase to execute
  the business logic for creating accounts.
* Author: Leo
*/
@RestController
@RequestMapping("/account") 
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    
    public AccountController(CreateAccountUseCase createAccount) {
        this.createAccountUseCase = createAccount;
    }

    /**
    * Name: create
    * Parameters: CreateAccountDto body - The data transfer object containing the information needed to create an account.
    * Description: This method handles POST requests to the /account/create endpoint. It takes a CreateAccountDto object
    * as input, which contains the necessary information to create a new account.
    * Author: Leo
    */
    @PostMapping("/create")
    public Account create(@RequestBody CreateAccountDto body) {
        return createAccountUseCase.execute(body);
    }

    @DeleteMapping("/delete")
    public String delete() {
        return "Delete account endpoint";
    }

    @PatchMapping("/edit")
    public String edit() {
        return "Edit account endpoint";
    }
}