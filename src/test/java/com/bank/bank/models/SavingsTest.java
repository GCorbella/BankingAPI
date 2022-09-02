package com.bank.bank.models;

import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.Savings;
import com.bank.bank.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SavingsTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Given an String, the method adds the prefix.")
    void Savings_addPrefix_Savings() {
        String string = "1425-655-0938";
        accountRepository.save(new Savings(string));
        assertEquals("SAV-1425-655-0938", accountRepository.findById("SAV-1425-655-0938").get().getId() );
    }
}
