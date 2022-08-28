package com.bank.bank.repositories;

import com.bank.bank.models.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}
