package com.bank.bank.models;

import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.utils.Money;
import com.bank.bank.repositories.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    @Test
    @DisplayName("Given an Account with a date that should apply a maintenance fee, the method applies it.")
    void Checking_applyMaintenanceFee_Checking() {
        AccountHolder accountHolder = new AccountHolder();
        LocalDate creationDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 2, LocalDate.now().getDayOfMonth());
        Checking checking = new Checking("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
                );
        checking.setMaintenanceDate(creationDate);
        checking.applyMaintenanceFee();
        assertEquals(new Money(BigDecimal.valueOf(26)).getAmount(), checking.getBalance().getAmount());
    }

    @Test
    @DisplayName("Given an Account with a date that shouldn't apply a maintenance fee, the method does not applies it.")
    void Checking_doesntApplyMaintenanceFee_Checking() {
        AccountHolder accountHolder = new AccountHolder();
        LocalDate creationDate = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        Checking checking = new Checking("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        checking.setMaintenanceDate(creationDate);
        checking.applyMaintenanceFee();
        assertEquals(new Money(BigDecimal.valueOf(50)).getAmount(), checking.getBalance().getAmount());
    }

    @Test
    @DisplayName("The method it applies the Penalty Fee.")
    void Checking_applyPenaltyFee_Checking() {
        AccountHolder accountHolder = new AccountHolder();
        Checking checking = new Checking("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        checking.applyPenaltyFee();
        assertEquals(new Money(BigDecimal.valueOf(10)).getAmount(), checking.getBalance().getAmount());
    }
}
