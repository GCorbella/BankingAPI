package com.bank.bank.models;

import com.bank.bank.models.utils.User;

import javax.persistence.Entity;

@Entity
public class Admin extends User{

    private String name;

    public Admin() {
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    //getter
    public String getName() {
        return name;
    }
}
