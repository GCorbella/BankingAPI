package com.bank.bank.controllers.dto;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.utils.Money;

import java.math.BigDecimal;

public class AccountDTO {
    private String accountType;
    private String id;
    private Money balance;
    private String secretKey;
    private Money minimumBalance;
    private Money creditLimit;
    private BigDecimal interestRate;

    //constructor
    public AccountDTO() {
    }

    public AccountDTO(String accountType, String id, Money balance, String secretKey) {
        this.accountType = accountType;
        this.id = id;
        this.balance = balance;
        this.secretKey = secretKey;
    }

    //setters
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    //getters
    public String getAccountType() {
        return accountType;
    }

    public String getId() {
        return id;
    }

    public Money getBalance() {
        return balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }
}
