package com.bank.bank.models.accounts;

import com.bank.bank.models.utils.Money;

public class Checking extends Account{
    private Money minimumBalance;
    private Money penaltyFee;
    private Money monthlyMaintenanceFee;
}
