package com.bank.bank.services;

import com.bank.bank.controllers.dto.AccountHolderDTO;
import com.bank.bank.repositories.RoleRepository;
import com.bank.bank.serializer.LocalDateDeserializer;
import com.bank.bank.serializer.LocalDateSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RoleRepository roleRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
            .registerTypeAdapter(LocalDate.class, new LocalDateSerializer());
    Gson gson = gsonBuilder.setPrettyPrinting().create();


    @Test
    @DisplayName("Creating an AccountHolder.")
    public void createAccountHolder() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO("gaita","gaita","Laia",
                "Gaytera", LocalDate.of(2000, 3, 22), "Calle", 7,
                "35667", "Calle", 7, "35667", "987654321",
                "Laya@Lalla.laja");

        String body = gson.toJson(accountHolderDTO);
        MvcResult mvcResult = mockMvc.perform(post("/new-user")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Gaytera"));
    }

}
