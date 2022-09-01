package com.bank.bank.models;

import com.bank.bank.models.utils.User;

import javax.persistence.Entity;

@Entity
public class Admin extends User{

    private String name;

    public Admin() {
    }

    public Admin(String username, String password, String name) {
        setUsername(username);
        setPassword(password);
        setName(name);
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
