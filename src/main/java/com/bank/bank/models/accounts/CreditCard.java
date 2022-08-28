package com.bank.bank.models.accounts;

import com.bank.bank.models.utils.Money;

import java.math.BigDecimal;

public class CreditCard extends Account{
    private Money creditLimit;
    private BigDecimal interestRate;
    private Money penaltyFee;

}
