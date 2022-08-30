package com.bank.bank.controllers.dto;

import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

public class TransferInfo {

    private String originAccountId;
    private String firstName;
    private String lastName;
    private String destinyAccountId;
    private BigDecimal amount;

    //constructor
    public TransferInfo() {
    }

    //setters
    public void setOriginAccountId(String originAccountId) {
        this.originAccountId = originAccountId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDestinyAccountId(String destinyAccountId) {
        this.destinyAccountId = destinyAccountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    //getters
    public String getOriginAccountId() {
        return originAccountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDestinyAccountId() {
        return destinyAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}


