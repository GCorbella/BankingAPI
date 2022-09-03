package com.bank.bank.models;

import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.Savings;
import com.bank.bank.models.utils.Money;
import com.bank.bank.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Test
    @DisplayName("The method it applies the Penalty Fee.")
    void Savings_applyPenaltyFee_Savings() {
        AccountHolder accountHolder = new AccountHolder();
        Savings savings = new Savings("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        savings.applyPenaltyFee();
        assertEquals(new Money(BigDecimal.valueOf(10)).getAmount(), savings.getBalance().getAmount());
    }

    @Test
    @DisplayName("Given an Account with a date that should apply an interest fee, the method applies it.")
    void Savings_applyInterest_Savings() {
        AccountHolder accountHolder = new AccountHolder();
        LocalDate creationDate = LocalDate.of(LocalDate.now().getYear() - 2, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        Savings savings = new Savings("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        savings.setInterestDate(creationDate);
        savings.applyInterest();
        assertEquals(new Money(BigDecimal.valueOf(50.2503125)).getAmount(), savings.getBalance().getAmount());
    }

    @Test
    @DisplayName("Given an Account with a date that shouldn't apply an interest fee, the method doesn't applies it.")
    void Savings_DoesntApplyInterest_Savings() {
        AccountHolder accountHolder = new AccountHolder();
        LocalDate creationDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        Savings savings = new Savings("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        savings.setInterestDate(creationDate);
        savings.applyInterest();
        assertEquals(new Money(BigDecimal.valueOf(50)).getAmount(), savings.getBalance().getAmount());
    }
}
