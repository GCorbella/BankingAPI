package com.bank.bank.services;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.accounts.Account;
import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.CreditCard;
import com.bank.bank.models.accounts.Savings;
import com.bank.bank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<Object[]> checkBalance(String username) {
        if (accountRepository.findByUsername(username).isPresent()){
            AccountHolder accountHolder = accountRepository.findByUsername(username).get();
            return accountRepository.checkBalance(accountHolder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This account/s doesn't exist.");
        }
    }

    public List<Object[]> myAccount(String username) {
        if (accountRepository.findByUsername(username).isPresent()){
            AccountHolder accountHolder = accountRepository.findByUsername(username).get();
            List<Account> primaryAccounts = accountHolder.getPrimaryAccounts();
            List<Account> secondaryAccounts = accountHolder.getSecondaryAccounts();
            applyInterestOrMaintenance(primaryAccounts, secondaryAccounts);
            return accountRepository.checkBalance(accountHolder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "This account/s doesn't exist or is/are not related to you.");
        }
    }

    public List<Object[]> allAccounts() {
        return accountRepository.allAccounts();
    }

    //auxiliary methods
    public void applyInterestOrMaintenance(List<Account> primaryAccounts, List<Account> secondaryAccounts) {
        for (Account primaryAccount : primaryAccounts) {
            if (primaryAccount instanceof Savings) {
                ((Savings) primaryAccount).applyInterest();
            } else if (primaryAccount instanceof CreditCard) {
                ((CreditCard) primaryAccount).applyInterest();
            } else if (primaryAccount instanceof Checking) {
                ((Checking) primaryAccount).applyMaintenanceFee();
            }
        }
        for (Account secondaryAccount : secondaryAccounts) {
            if (secondaryAccount instanceof Savings) {
                ((Savings) secondaryAccount).applyInterest();
            } else if (secondaryAccount instanceof CreditCard) {
                ((CreditCard) secondaryAccount).applyInterest();
            } else if (secondaryAccount instanceof Checking) {
                ((Checking) secondaryAccount).applyMaintenanceFee();
            }
        }

    }
}
