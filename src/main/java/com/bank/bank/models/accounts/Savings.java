package com.bank.bank.models.accounts;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.utils.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Savings extends Account{
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))})
    private Money minimumBalance = new Money(BigDecimal.valueOf(1000));//the default minimum balance is 1000, but it can be lowered until 100
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))})
    private Money penaltyFee = new Money(BigDecimal.valueOf(40));
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025); //the default value of the interest rate is 0.0025, and the maximum is 0.5
    private LocalDate interestDate = getCreationDate();

    //constructor
    public Savings() {
    }

    public Savings(String id, Money balance, String secretKey, AccountHolder primaryOwner, LocalDate creationDate,
                   boolean status, Money minimumBalance, BigDecimal interestRate) {
        setId(id);
        setBalance(balance);
        setSecretKey(secretKey);
        setPrimaryOwner(primaryOwner);
        setCreationDate(creationDate);
        setStatus(status);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    //methods
    public void applyPenaltyFee() {
        this.setBalance(new Money(this.getBalance().decreaseAmount(penaltyFee.getAmount())));
    }

    public void applyInterest(){
        if (LocalDate.now().getYear() > this.getInterestDate().getYear()) {
            for (int i = LocalDate.now().getYear(); i > this.getInterestDate().getYear() ; i--) {
                this.setBalance(new Money(this.getBalance().increaseAmount(this.getBalance().getAmount().multiply(interestRate.add(BigDecimal.valueOf(1))))));
            }
            setInterestDate(LocalDate.now());
        }
    }

    //setters
    @Override
    public void setId(String id){
        setId("SAV-" + id);
    }
    public void setMinimumBalance(Money minimumBalance) {
        //the minimum balance must be 100, if it's less, the script will set 100 anyway.
        if (minimumBalance.getAmount().doubleValue() < 100){
            this.minimumBalance = new Money(BigDecimal.valueOf(100));
        } else {
            this.minimumBalance = minimumBalance;
        }
    }

    public void setPenaltyFee(Money penaltyFee) {
    }

    public void setInterestRate(BigDecimal interestRate) {
        //the maximum interest rate must be 0.5, if it's more, the script will limit it to = 0.5.
        if (interestRate.doubleValue() > 0.5){
            this.interestRate = BigDecimal.valueOf(0.5);
        }
        else {
            this.interestRate = interestRate;
        }
    }

    public void setInterestDate(LocalDate interestDate) {
        this.interestDate = interestDate;
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

    public LocalDate getInterestDate() {
        return interestDate;
    }
}
