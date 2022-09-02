package com.bank.bank.models;

import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.CreditCard;
import com.bank.bank.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreditCardTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Given an String, the method adds the prefix.")
    void CreditCard_addPrefix_CreditCard() {
        String string = "1425-655-0938";
        accountRepository.save(new CreditCard(string));
        assertEquals("CRE-1425-655-0938", accountRepository.findById("CRE-1425-655-0938").get().getId() );
    }
}
