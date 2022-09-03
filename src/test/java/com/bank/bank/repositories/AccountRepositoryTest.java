package com.bank.bank.repositories;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.CreditCard;
import com.bank.bank.models.accounts.Savings;
import com.bank.bank.models.utils.Address;
import com.bank.bank.models.utils.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountRepositoryTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AddressRepository addressRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Given an AccountHolder, the method returns all of its accounts balance.")
    void checkBalanceTest() throws Exception {
        Address address = new Address("asdsadad", 2, "adadas");
        addressRepository.save(address);
        AccountHolder accountHolder = new AccountHolder("4058964", "asdasda", "Gallo",
                "Malayo", LocalDate.now(), address, address, "321654987", "adas@asdsaasd.asc");
        accountHolderRepository.save(accountHolder);
        CreditCard creditCard = new CreditCard("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        Savings savings = new Savings("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        Checking checking = new Checking("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        accountRepository.save(creditCard);
        accountRepository.save(savings);
        accountRepository.save(checking);
        List<Object[]> accounts = accountRepository.checkBalance(accountHolder);
        MvcResult mvcResult = mockMvc.perform(get("/check-balance?username=4058964"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CHE-asas"));
    }

    @Test
    @DisplayName("The method returns all of the accounts id and owners.")
    void allAccountsTest() throws Exception {
        Address address = new Address("asdsadad", 2, "adadas");
        addressRepository.save(address);
        AccountHolder accountHolder = new AccountHolder("4058964", "asdasda", "Gallo",
                "Malayo", LocalDate.now(), address, address, "321654987", "adas@asdsaasd.asc");
        accountHolderRepository.save(accountHolder);
        CreditCard creditCard = new CreditCard("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        Savings savings = new Savings("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        Checking checking = new Checking("asas",
                new Money(BigDecimal.valueOf(50)),
                "asdasasd",
                accountHolder,
                accountHolder,
                true
        );
        accountRepository.save(creditCard);
        accountRepository.save(savings);
        accountRepository.save(checking);
        List<Object[]> accounts = accountRepository.allAccounts();
        MvcResult mvcResult = mockMvc.perform(get("/all-accounts"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CHE-asas"));
    }
}
