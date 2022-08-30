package com.bank.bank.services;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.accounts.Account;
import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.CreditCard;
import com.bank.bank.models.accounts.Savings;
import com.bank.bank.models.utils.Money;
import com.bank.bank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
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

    public void transfer(UserDetails userDetails, String originAccountId, String firstName, String lastName, String destinyAccountId, Money amount) {
        if (accountRepository.findByUsername(userDetails.getUsername()).isPresent()) {
            AccountHolder accountHolder = accountRepository.findByUsername(userDetails.getUsername()).get();
            if (accountRepository.findById(originAccountId).isPresent()) {
                Account originAccount = accountRepository.findById(originAccountId).get();
                if (originAccount.getPrimaryOwner().equals(accountHolder)) {
                    if (accountRepository.findById(destinyAccountId).isPresent()) {
                        Account destinyAccount = accountRepository.findById(destinyAccountId).get();
                        if (destinyAccount.getPrimaryOwner().getFirstName().equals(firstName) && destinyAccount.getPrimaryOwner().getLastName().equals(lastName)) {
                            if (originAccount.getBalance().getAmount().compareTo(amount.getAmount()) >= 0) {
                                originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(amount)));
                                destinyAccount.setBalance(new Money(destinyAccount.getBalance().increaseAmount(amount)));
                                if (originAccount instanceof Checking || originAccount instanceof Savings) {
                                    if (originAccount.getBalance().getAmount().compareTo(((Checking) originAccount).getMinimumBalance().getAmount()) < 0) {
                                        ((Checking) originAccount).applyPenaltyFee();
                                    } else if (originAccount.getBalance().getAmount().compareTo(((Savings) originAccount).getMinimumBalance().getAmount()) < 0) {
                                        ((Savings) originAccount).applyPenaltyFee();
                                    }
                                }
                            } else {
                                throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "The balance in your origin account is not enough to fulfill that operation.");
                            }
                        } else if (destinyAccount.getSecondaryOwner().getFirstName().equals(firstName) && destinyAccount.getSecondaryOwner().getLastName().equals(lastName)) {

                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The destiny account doesn't exist or it's not attached to that person.");
                        }
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The destiny account doesn't exist.");
                    }
                } else {
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The origin account is not attached to you or you are not the primary owner.");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The origin account number is wrong or invalid.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This username is not attached to any client of this bank.");
        }

        if (accountRepository.findByUsername(userDetails.getUsername()).isPresent()) {
            AccountHolder accountHolder = accountRepository.findByUsername(userDetails.getUsername()).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This username is not attached to any client of this bank.");
        }

        if (accountRepository.findById(originAccountId).isPresent()) {
            Account originAccount = accountRepository.findById(originAccountId).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The origin account number is wrong or invalid.");
        }
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
