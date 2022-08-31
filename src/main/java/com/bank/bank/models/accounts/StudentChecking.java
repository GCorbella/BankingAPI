package com.bank.bank.models.accounts;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.utils.Money;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class StudentChecking extends Account{

    public StudentChecking() {
    }

    public StudentChecking(String id, Money balance, String secretKey, AccountHolder primaryOwner, LocalDate creationDate, boolean status) {
        setId(id);
        setBalance(balance);
        setSecretKey(secretKey);
        setPrimaryOwner(primaryOwner);
        setCreationDate(creationDate);
        setStatus(status);
    }

    //setters
    @Override
    public void setId(String id){
        setId("SCH-" + id);
    }
}
