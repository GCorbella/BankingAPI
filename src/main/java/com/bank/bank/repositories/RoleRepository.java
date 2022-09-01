package com.bank.bank.repositories;

import com.bank.bank.models.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Optional<Role> findByRole(String role);
}
