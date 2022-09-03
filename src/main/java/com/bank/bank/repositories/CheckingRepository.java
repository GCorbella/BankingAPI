package com.bank.bank.repositories;

import com.bank.bank.models.accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckingRepository extends JpaRepository<Checking, String> {
}
