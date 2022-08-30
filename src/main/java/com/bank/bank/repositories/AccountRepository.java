package com.bank.bank.repositories;

import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<AccountHolder> findByUsername(String username);

    @Query(value = "SELECT id, amount, currency FROM account WHERE primary_owner = accountHolder OR secondary_owner = accountHolder", nativeQuery = true)
    List<Object[]> checkBalance(AccountHolder accountHolder);

    @Query(value = "SELECT id, primary_owner, secondary_owner FROM account", nativeQuery = true)
    List<Object[]> allAccounts();
}
