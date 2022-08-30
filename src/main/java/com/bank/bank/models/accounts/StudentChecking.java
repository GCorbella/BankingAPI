package com.bank.bank.models.accounts;

import javax.persistence.Entity;

@Entity
public class StudentChecking extends Account{

    public StudentChecking() {
    }

    //setters
    @Override
    public void setId(String id){
        setId("SCH-" + id);
    }
}
