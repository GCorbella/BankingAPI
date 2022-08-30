package com.bank.bank.models;

import com.bank.bank.models.accounts.Account;
import com.bank.bank.models.utils.Address;
import com.bank.bank.models.utils.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;

@Entity
public class AccountHolder extends User{
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @OneToOne
    @JoinColumn(name = "primary_address")
    private Address primaryAddress;
    @OneToOne
    @JoinColumn(name = "mailing_address")
    private Address mailingAddress;
    private String phoneNumber;
    private String eMail;
    @OneToMany(mappedBy = "primaryOwner")
    private List<Account> primaryAccounts;
    @OneToMany(mappedBy = "secondaryOwner")
    private List<Account> secondaryAccounts;

    public AccountHolder() {
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPrimaryAccounts(List<Account> primaryAccounts) {
        this.primaryAccounts = primaryAccounts;
    }

    public void setSecondaryAccounts(List<Account> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }

    //getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public List<Account> getPrimaryAccounts() {
        return primaryAccounts;
    }

    public List<Account> getSecondaryAccounts() {
        return secondaryAccounts;
    }
}
