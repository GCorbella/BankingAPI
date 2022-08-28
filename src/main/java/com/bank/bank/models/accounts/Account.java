package com.bank.bank.models.accounts;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.utils.Money;

import java.time.LocalDate;

public abstract class Account {
    private String id;
    private Money balance;
    private String secretKey;
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    private LocalDate creationDate;
    private boolean Status;

    public Account() {
    }

    //setters
    public void setId(String id) {
        this.id = id;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    //getters
    public String getId() {
        return id;
    }

    public Money getBalance() {
        return balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isStatus() {
        return Status;
    }
}
