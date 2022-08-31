package com.bank.bank.services;

import com.bank.bank.controllers.dto.AccountHolderDTO;
import com.bank.bank.models.AccountHolder;
import com.bank.bank.repositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        if (LocalDate.now().getYear() - accountHolderDTO.getDateOfBirth().getYear() < 18) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You must be older than 18 to create an account.");
        }

    }
}
