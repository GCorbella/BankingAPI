package com.bank.bank.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ThirdParty {
    @Id
    private Long id;
    private String name;
    private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String name, String hashedKey) {
        setName(name);
        setHashedKey(hashedKey);
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    //getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHashedKey() {
        return hashedKey;
    }
}
