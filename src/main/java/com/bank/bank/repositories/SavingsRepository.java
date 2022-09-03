package com.bank.bank.repositories;

import com.bank.bank.models.accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, String> {
}
