package com.bank.bank.models.accounts;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.utils.Money;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Checking extends Account{
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount"))})
    private Money minimumBalance = new Money(BigDecimal.valueOf(250));
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "penalty_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "penalty_fee_amount"))})
    private Money penaltyFee = new Money(BigDecimal.valueOf(40));
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency")),
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount"))})
    private Money monthlyMaintenanceFee = new Money(BigDecimal.valueOf(12));
    private LocalDate MaintenanceDate = LocalDate.now();

    //constructors
    public Checking() {
    }

    public Checking(String id, Money balance, String secretKey, AccountHolder primaryOwner, LocalDate creationDate, boolean status) {
        setId(id);
        setBalance(balance);
        setSecretKey(secretKey);
        setPrimaryOwner(primaryOwner);
        setCreationDate(creationDate);
        setStatus(status);
    }

    public Checking(String string) {
        setId(string);
    }

    public Checking(String id, Money money, String secretKey, AccountHolder primaryHolder,
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

    public void applyMaintenanceFee(){
        if (LocalDate.now().getMonthValue() > this.getMaintenanceDate().getMonthValue() && LocalDate.now().getYear() >= this.getMaintenanceDate().getYear()) {
            for (int i = LocalDate.now().getMonthValue(); i > this.getMaintenanceDate().getMonthValue() ; i--) {
                this.setBalance(new Money(this.getBalance().decreaseAmount(monthlyMaintenanceFee)));
            }
            this.setMaintenanceDate(LocalDate.now());
        }
    }

    //setters
    @Override
    public void setId(String id){
        super.id = "CHE-".concat(id);
    }
    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public void setPenaltyFee(Money penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        MaintenanceDate = maintenanceDate;
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

    public LocalDate getMaintenanceDate() {
        return MaintenanceDate;
    }
}

