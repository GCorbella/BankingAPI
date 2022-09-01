package com.bank.bank.models.utils;

import com.bank.bank.models.AccountHolder;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private AccountHolder accountHolder;
    private String street;
    private int portal;
    private String otherInfo;

    public Address() {
    }

    public Address(String street, int portal, String streetOtherInfo) {
        setStreet(street);
        setPortal(portal);
        setOtherInfo(streetOtherInfo);
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPortal(int portal) {
        this.portal = portal;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    //getters
    public Long getId() {
        return id;
    }

    public AccountHolder getAccountHolder() {
        return accountHolder;
    }

    public String getStreet() {
        return street;
    }

    public int getPortal() {
        return portal;
    }

    public String getOtherInfo() {
        return otherInfo;
    }
}
