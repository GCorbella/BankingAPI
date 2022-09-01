package com.bank.bank.services;

import com.bank.bank.controllers.dto.AccountHolderDTO;
import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.utils.Address;
import com.bank.bank.repositories.AccountHolderRepository;
import com.bank.bank.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class AccountHolderService {

    @Autowired
    private final AccountHolderRepository accountHolderRepository;

    @Autowired
    private final RoleRepository roleRepository;

    //constructor
    public AccountHolderService(AccountHolderRepository accountHolderRepository, RoleRepository roleRepository) {
        this.accountHolderRepository = accountHolderRepository;
        this.roleRepository = roleRepository;
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO) {
        if (LocalDate.now().getYear() - accountHolderDTO.getDateOfBirth().getYear() < 18) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You must be older than 18 to create an account.");
        }
        Address primaryAddress = new Address(accountHolderDTO.getStreet(),
                accountHolderDTO.getPortal(),
                accountHolderDTO.getStreetOtherInfo());
        Address mailingAddress = new Address(accountHolderDTO.getMailingStreet(),
                accountHolderDTO.getMailingPortal(),
                accountHolderDTO.getMailingStreetOtherInfo());
        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getUsername(),
                accountHolderDTO.getPassword(),
                accountHolderDTO.getFirstName(),
                accountHolderDTO.getLastName(),
                accountHolderDTO.getDateOfBirth(),
                primaryAddress,
                mailingAddress,
                accountHolderDTO.getPhoneNumber(),
                accountHolderDTO.geteMail()
                );
        accountHolder.addRole(roleRepository.findByRole("USER").get());
        return accountHolder;
    }
}
