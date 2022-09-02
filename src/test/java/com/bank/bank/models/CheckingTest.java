package com.bank.bank.models;

import com.bank.bank.models.accounts.Checking;
import com.bank.bank.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CheckingTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Given an String, the method adds the prefix.")
    void Checking_addPrefix_Checking() {
        String string = "1425-655-0938";
        accountRepository.save(new Checking(string));
        assertEquals("CHE-1425-655-0938", accountRepository.findById("CHE-1425-655-0938").get().getId() );
    }
}
