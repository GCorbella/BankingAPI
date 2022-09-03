package com.bank.bank.repositories;

import com.bank.bank.models.ThirdParty;
import com.bank.bank.models.utils.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ThirdPartyRepositoryTest {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Test
    @DisplayName("The method return the Third Party that contains the Hashed Key as the parameter.")
    void findByHashedKeyTest() {
        ThirdParty test = new ThirdParty("Name", "asdsadasd");
        thirdPartyRepository.save(test);
        ThirdParty thirdParty = thirdPartyRepository.findByHashedKey("asdsadasd").get();
        assertTrue(thirdParty.getHashedKey().equals("asdsadasd"));
    }
}
