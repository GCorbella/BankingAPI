package com.bank.bank.controllers.dto;

import java.time.LocalDate;

public class AccountHolderDTO {
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String street;
    int portal;
    String streetOtherInfo;
    String mailingStreet;
    int mailingPortal;
    String mailingStreetOtherInfo;
    String phoneNumber;
    String eMail;

    //constructors
    public AccountHolderDTO() {
    }

    public AccountHolderDTO(String username, String password, String firstName, String lastName,
                            LocalDate dateOfBirth, String street, int portal, String streetOtherInfo,
                            String mailingStreet, int mailingPortal, String mailingStreetOtherInfo,
                            String phoneNumber, String eMail) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.street = street;
        this.portal = portal;
        this.streetOtherInfo = streetOtherInfo;
        this.mailingStreet = mailingStreet;
        this.mailingPortal = mailingPortal;
        this.mailingStreetOtherInfo = mailingStreetOtherInfo;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
    }

    //setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPortal(int portal) {
        this.portal = portal;
    }

    public void setStreetOtherInfo(String streetOtherInfo) {
        this.streetOtherInfo = streetOtherInfo;
    }

    public void setMailingStreet(String mailingStreet) {
        this.mailingStreet = mailingStreet;
    }

    public void setMailingPortal(int mailingPortal) {
        this.mailingPortal = mailingPortal;
    }

    public void setMailingStreetOtherInfo(String mailingStreetOtherInfo) {
        this.mailingStreetOtherInfo = mailingStreetOtherInfo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    //getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getStreet() {
        return street;
    }

    public int getPortal() {
        return portal;
    }

    public String getStreetOtherInfo() {
        return streetOtherInfo;
    }

    public String getMailingStreet() {
        return mailingStreet;
    }

    public int getMailingPortal() {
        return mailingPortal;
    }

    public String getMailingStreetOtherInfo() {
        return mailingStreetOtherInfo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }
}
