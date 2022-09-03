package com.bank.bank.services;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.ThirdParty;
import com.bank.bank.models.accounts.CreditCard;
import com.bank.bank.models.utils.Address;
import com.bank.bank.models.utils.Money;
import com.bank.bank.repositories.AccountHolderRepository;
import com.bank.bank.repositories.AccountRepository;
import com.bank.bank.repositories.AddressRepository;
import com.bank.bank.repositories.ThirdPartyRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ThirdPartyServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;
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
    @DisplayName("The method creates a new Thir Party")
    void CreateThirdPartyTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/new-third-party")
                        .param("name", "TestParty")
                        .param("hashedKey", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(mvcResult.getResponse().getContentAsString().contains("TestParty"));
        assertTrue((thirdPartyRepository.findById(1L)).isPresent());
    }

    @Test
    @DisplayName("Given all the correct parameters, the account receive the money from the Third Party")
    void ReceiveFromThirdPartyTest() throws Exception {
        ThirdParty thirdParty = new ThirdParty("adasa", "test");
        thirdPartyRepository.save(thirdParty);
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
        accountRepository.save(creditCard);

        MvcResult mvcResult = mockMvc.perform(patch("/send/test")
                        .param("amount", "30")
                        .param("accountId", "CRE-asas")
                        .param("accountSecretKey", "asdasasd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertEquals(80.00,accountRepository.findById("CRE-asas").get().getBalance().getAmount().doubleValue());
    }

    @Test
    @DisplayName("Given an incorrect Hashed Key, it returns the correct exception message.")
    void WrongHashedKeyReceiveFromThirdPartyTest() throws Exception {
        ThirdParty thirdParty = new ThirdParty("adasa", "test");
        thirdPartyRepository.save(thirdParty);
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
        accountRepository.save(creditCard);

        MvcResult mvcResult = mockMvc.perform(patch("/send/st")
                        .param("amount", "30")
                        .param("accountId", "CRE-asas")
                        .param("accountSecretKey", "asdasasd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted()).andReturn();

        assertTrue(mvcResult.getResolvedException()
                .getMessage()
                .contains("Wrong Hashed Key"));
    }

    @Test
    @DisplayName("Given an incorrect Account ID, it returns the correct exception message.")
    void WrongAccountIdReceiveFromThirdPartyTest() throws Exception {
        ThirdParty thirdParty = new ThirdParty("adasa", "test");
        thirdPartyRepository.save(thirdParty);
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
        accountRepository.save(creditCard);

        MvcResult mvcResult = mockMvc.perform(patch("/send/test")
                        .param("amount", "30")
                        .param("accountId", "CRE-s")
                        .param("accountSecretKey", "asdasasd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        assertTrue(mvcResult.getResolvedException()
                .getMessage()
                .contains("This account doesn't exist."));
    }

    @Test
    @DisplayName("Given an incorrect Secret Key, it returns the correct exception message.")
    void WrongSecretKeyReceiveFromThirdPartyTest() throws Exception {
        ThirdParty thirdParty = new ThirdParty("adasa", "test");
        thirdPartyRepository.save(thirdParty);
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
        accountRepository.save(creditCard);

        MvcResult mvcResult = mockMvc.perform(patch("/send/test")
                        .param("amount", "30")
                        .param("accountId", "CRE-asas")
                        .param("accountSecretKey", "sasd")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()).andReturn();

        assertTrue(mvcResult.getResolvedException()
                .getMessage()
                .contains("This account doesn't exist and/or the secret key is wrong."));
    }
}
