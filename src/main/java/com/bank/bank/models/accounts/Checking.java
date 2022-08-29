package com.bank.bank.models.accounts;

import com.bank.bank.models.utils.Money;

import javax.persistence.*;

@Entity
public class Checking extends Account{
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")), @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))})
    private Money minimumBalance;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")), @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))})
    private Money penaltyFee;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")), @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount"))})
    private Money monthlyMaintenanceFee;

    //constructor
    public Checking() {
    }

    //setters
    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    //getters
    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }
}

