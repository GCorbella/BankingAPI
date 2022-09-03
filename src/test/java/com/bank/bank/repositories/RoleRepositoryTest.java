package com.bank.bank.repositories;

import com.bank.bank.models.utils.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    @DisplayName("The method returns the role with the name as a parameter.")
    void findByRoleTest() {
    Role role = roleRepository.findByRole("ADMIN").get();
    assertTrue(role.getRole().equals("ADMIN"));
    }
}
