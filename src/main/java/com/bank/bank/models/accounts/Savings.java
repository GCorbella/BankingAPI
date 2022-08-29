package com.bank.bank.models.accounts;

import com.bank.bank.models.utils.Money;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Savings extends Account{
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")), @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))})
    private Money minimumBalance;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")), @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))})
    private Money penaltyFee;
    private BigDecimal interestRate;

    public Savings() {
    }

    //setters
    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    //getters
    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }
}
