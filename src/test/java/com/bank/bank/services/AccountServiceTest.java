package com.bank.bank.services;

import com.bank.bank.controllers.dto.AccountDTO;
import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.accounts.Checking;
import com.bank.bank.models.accounts.CreditCard;
import com.bank.bank.models.accounts.Savings;
import com.bank.bank.models.utils.Address;
import com.bank.bank.models.utils.Money;
import com.bank.bank.repositories.AccountHolderRepository;
import com.bank.bank.repositories.AccountRepository;
import com.bank.bank.repositories.AddressRepository;
import com.bank.bank.serializer.LocalDateDeserializer;
import com.bank.bank.serializer.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;
    @Autowired
    AddressRepository addressRepository;

    GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
    Gson gson = gsonBuilder.setPrettyPrinting().create();

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
    @DisplayName("Given a wrong AccountHolder username, the method returns the adequate message.")
    void checkBalanceFailTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/check-balance?username=4asd"))
                .andExpect(status().isNotFound())
                .andReturn();

        assertTrue(mvcResult.getResolvedException()
                .getMessage()
                .contains("This account/s doesn't exist."));
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

    @Test
    @DisplayName("The method returns all of the accounts of the owner.")
    void myAccountTest() throws Exception {
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
        MvcResult mvcResult = mockMvc.perform(get("/myaccount")
                        .with(user("4058964").password("asdasda")))
                .andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CHE-asas"));
    }

    @Test
    @DisplayName("The method creates a Students Checking.")
    void createStudentsCheckingAccountTest() throws Exception {
        Address address = new Address("asdsadad", 2, "adadas");
        addressRepository.save(address);
        AccountHolder accountHolder = new AccountHolder("4058964", "asdasda", "Gallo",
                "Malayo", LocalDate.now(), address, address, "321654987", "adas@asdsaasd.asc");
        accountHolderRepository.save(accountHolder);

        AccountDTO accountDTO = new AccountDTO("checking", "1", new Money(BigDecimal.valueOf(20)), "caca");
        String body = gson.toJson(accountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/create-account")
                        .with(user("admin").password("admin").roles("ADMIN"))
                        .param("accountHolderId", "1L")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("SCH-1"));
    }

    @Test
    @DisplayName("The method creates a Students Checking.")
    void createCheckingAccountTest() throws Exception {
        Address address = new Address("asdsadad", 2, "adadas");
        addressRepository.save(address);
        AccountHolder accountHolder = new AccountHolder("4058964", "asdasda", "Gallo",
                "Malayo", LocalDate.of(1980,4,10), address, address, "321654987", "adas@asdsaasd.asc");
        accountHolderRepository.save(accountHolder);

        AccountDTO accountDTO = new AccountDTO("checking", "1", new Money(BigDecimal.valueOf(20)), "caca");
        String body = gson.toJson(accountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/create-account")
                        .with(user("admin").password("admin").roles("ADMIN"))
                        .param("accountHolderId", "1L")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CHE-1"));
    }

    @Test
    @DisplayName("The method creates a Savings account.")
    void createSavingsAccountTest() throws Exception {
        Address address = new Address("asdsadad", 2, "adadas");
        addressRepository.save(address);
        AccountHolder accountHolder = new AccountHolder("4058964", "asdasda", "Gallo",
                "Malayo", LocalDate.of(1980,4,10), address, address, "321654987", "adas@asdsaasd.asc");
        accountHolderRepository.save(accountHolder);

        AccountDTO accountDTO = new AccountDTO("savings", "1", new Money(BigDecimal.valueOf(20)), "caca");
        String body = gson.toJson(accountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/create-account")
                        .with(user("admin").password("admin").roles("ADMIN"))
                        .param("accountHolderId", "1L")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("SAV-1"));
    }

    @Test
    @DisplayName("The method creates a Credit Card account.")
    void createCreditCartAccountTest() throws Exception {
        Address address = new Address("asdsadad", 2, "adadas");
        addressRepository.save(address);
        AccountHolder accountHolder = new AccountHolder("4058964", "asdasda", "Gallo",
                "Malayo", LocalDate.of(1980,4,10), address, address, "321654987", "adas@asdsaasd.asc");
        accountHolderRepository.save(accountHolder);

        AccountDTO accountDTO = new AccountDTO("creditcard", "1", new Money(BigDecimal.valueOf(20)), "caca");
        String body = gson.toJson(accountDTO);

        MvcResult mvcResult = mockMvc.perform(post("/create-account")
                        .with(user("admin").password("admin").roles("ADMIN"))
                        .param("accountHolderId", "1L")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("CRE-1"));
    }
}
