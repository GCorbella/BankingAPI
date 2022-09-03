package com.bank.bank.repositories;

import com.bank.bank.models.utils.Role;
import com.bank.bank.models.utils.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("The method returns the User with the Username as a parameter.")
    void findByUsernameTest() {
        User user = userRepository.findByUsername("admin").get();
        assertTrue(user.getUsername().equals("admin"));
    }
}
