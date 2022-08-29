package com.bank.bank.models.accounts;

import com.bank.bank.models.utils.Money;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class CreditCard extends Account{
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency")), @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount"))})
    private Money creditLimit;
    private BigDecimal interestRate;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")), @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))})
    private Money penaltyFee;

    public CreditCard() {
    }

    //setters
    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    //getters
    public Money getCreditLimit() {
        return creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public Money getPenaltyFee() {
        return penaltyFee;
    }
}
