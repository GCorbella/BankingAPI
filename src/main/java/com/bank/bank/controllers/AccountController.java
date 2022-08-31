package com.bank.bank.controllers;

import com.bank.bank.controllers.dto.AccountDTO;
import com.bank.bank.controllers.dto.TransferInfo;
import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.accounts.Account;
import com.bank.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/check-balance")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> checkBalance(@RequestParam String username) {
            return accountService.checkBalance(username);
    }

    @GetMapping("/myaccount")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> myAccount(@AuthenticationPrincipal UserDetails userDetails) {
        return accountService.myAccount(userDetails.getUsername());
    }

    @GetMapping("/all-accounts")
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> allAccounts() {
        return accountService.allAccounts();
    }

    @PostMapping("/create-account")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createAccount(@RequestBody AccountDTO accountDTO, @RequestParam Long accountHolderId) {
        return accountService.createAccount(accountDTO, accountHolderId);
    }

    @PatchMapping("/myaccount/transfer")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void transfer(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TransferInfo transferInfo) {
        accountService.transfer(userDetails, transferInfo);
    }

    @PatchMapping("/modify-balance")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Account modifyBalance(@RequestParam String accountID, @RequestParam BigDecimal newBalance) {
        return accountService.modifyBalance(accountID, newBalance);
    }
}
