package com.bank.bank.models.accounts;

import com.bank.bank.models.utils.Money;

import java.math.BigDecimal;

public class Savings extends Account{
    private Money minimumBalance;
    private Money penaltyFee;
    private BigDecimal interestRate;
}
