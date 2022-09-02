package com.bank.bank.models;

import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.StudentChecking;
import com.bank.bank.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StudentCheckingTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Given an String, the method adds the prefix.")
    void StudentChecking_addPrefix_StudentChecking() {
        String string = "1425-655-0938";
        accountRepository.save(new StudentChecking(string));
        assertEquals("SCH-1425-655-0938", accountRepository.findById("SCH-1425-655-0938").get().getId() );
    }
}
