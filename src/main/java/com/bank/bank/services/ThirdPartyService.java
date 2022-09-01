package com.bank.bank.services;

import com.bank.bank.models.ThirdParty;
import com.bank.bank.models.accounts.Account;
import com.bank.bank.models.utils.Money;
import com.bank.bank.repositories.AccountRepository;
import com.bank.bank.repositories.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class ThirdPartyService {

    @Autowired
    private static ThirdPartyRepository thirdPartyRepository;
    @Autowired
    private static AccountRepository accountRepository;

    static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static ThirdParty createThirdParty(String name, String hashedKey) {
        ThirdParty thirdParty = new ThirdParty(name,
                passwordEncoder.encode(hashedKey));
        thirdPartyRepository.save(thirdParty);
        return thirdParty;
    }

    public static void receiveFromThirdParty(String hashedKey, int amount, String accountId, String accountSecretKey) {
        if (thirdPartyRepository.findByHashedKey(hashedKey).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Hashed Key");
        }
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account doesn't exist.");
        }
        Account account = accountRepository.findById(accountId).get();
        if (!account.getSecretKey().equals(accountSecretKey)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account doesn't exist and/or the secret key is wrong.");
        }
        account.setBalance(new Money(account.getBalance().increaseAmount(new BigDecimal(amount))));
    }
}
