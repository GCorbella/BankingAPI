package com.bank.bank.models.accounts;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.utils.Money;
import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class CreditCard extends Account{
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "credit_limit_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit_amount"))})
    private Money creditLimit = new Money(BigDecimal.valueOf(100)); //the default credit limit is 100, but it can be increased until 100000
    private BigDecimal interestRate = BigDecimal.valueOf(0.2); //the default interest rate is 0.2, but it can be decreased until 0.1
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))})
    private Money penaltyFee = new Money(BigDecimal.valueOf(40));
    private LocalDate interestDate = getCreationDate();

    //constructors
    public CreditCard() {
    }

    public CreditCard(String id, Money balance, String secretKey, AccountHolder primaryOwner, LocalDate creationDate,
                      boolean status, Money creditLimit, BigDecimal interestRate) {
        setId(id);
        setBalance(balance);
        setSecretKey(secretKey);
        setPrimaryOwner(primaryOwner);
        setCreationDate(creationDate);
        setStatus(status);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);
    }

    public CreditCard(String string) {
        setId(string);
    }

    public CreditCard(String id, Money money, String secretKey, AccountHolder primaryHolder,
                      AccountHolder secondaryHolder, boolean status) {
        setId(id);
        setBalance(money);
        setSecretKey(secretKey);
        setPrimaryOwner(primaryHolder);
        setSecondaryOwner(secondaryHolder);
        setStatus(status);
    }

    //methods
    public void applyPenaltyFee() {
        this.setBalance(new Money(this.getBalance().decreaseAmount(penaltyFee.getAmount())));
    }

    public void applyInterest(){
        if (LocalDate.now().getMonthValue() > this.getInterestDate().getMonthValue() &&
                LocalDate.now().getYear() >= this.getInterestDate().getYear()) {
            for (int i = LocalDate.now().getMonthValue(); i > this.getInterestDate().getMonthValue() ; i--) {
                this.setBalance(new Money(this.getBalance().increaseAmount(this.getBalance().getAmount().multiply(interestRate))));
            }
            setInterestDate(LocalDate.now());
        }
    }

    //setters
    @Override
    public void setId(String id){
        super.id = "CRE-".concat(id);
    }
    public void setCreditLimit(Money creditLimit) {
        //the maximum credit limit is 100000. Also, if it's lower than 100, it stays at 100 or the credit card will be useless.
        if (creditLimit.getAmount().doubleValue() > 100000){
            this.creditLimit = new Money(BigDecimal.valueOf(100000));
        } else if (creditLimit.getAmount().doubleValue() < 100) {
            this.creditLimit = new Money(BigDecimal.valueOf(100));
        } else {
            this.creditLimit = creditLimit;
        }
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.doubleValue() < 0.1) {
            this.interestRate = BigDecimal.valueOf(0.1);
        } else {
            this.interestRate = interestRate;
        }
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public void setInterestDate(LocalDate interestDate) {
        this.interestDate = interestDate;
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

    public LocalDate getInterestDate() {
        return interestDate;
    }
}
