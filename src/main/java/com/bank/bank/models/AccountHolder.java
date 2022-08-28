package com.bank.bank.models;

import com.bank.bank.models.utils.Address;
import com.bank.bank.models.utils.User;

import java.time.LocalDate;

public class AccountHolder {
    private Long id;
    private User user;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Address primaryAddress;
    private Address mailingAddress;
    private String phoneNumber;
    private String eMail;

}
