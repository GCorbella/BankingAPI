package com.bank.bank.services;

import com.bank.bank.controllers.dto.AccountDTO;
import com.bank.bank.controllers.dto.TransferInfo;
import com.bank.bank.models.AccountHolder;
import com.bank.bank.models.accounts.*;
import com.bank.bank.models.utils.Money;
import com.bank.bank.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private static AccountRepository accountRepository;
    @Autowired
    private final CheckingRepository checkingRepository;
    @Autowired
    private final StudentCheckingRepository studentCheckingRepository;
    @Autowired
    private final SavingsRepository savingsRepository;
    @Autowired
    private final CreditCardRepository creditCardRepository;
    @Autowired
    private final AccountHolderRepository accountHolderRepository;
    @Autowired
    private static ThirdPartyRepository thirdPartyRepository;

    //service constructor
    public AccountService(AccountRepository accountRepository, CheckingRepository checkingRepository, StudentCheckingRepository studentCheckingRepository, SavingsRepository savingsRepository, CreditCardRepository creditCardRepository, AccountHolderRepository accountHolderRepository, ThirdPartyRepository thirdPartyRepository) {
        AccountService.accountRepository = accountRepository;
        this.checkingRepository = checkingRepository;
        this.studentCheckingRepository = studentCheckingRepository;
        this.savingsRepository = savingsRepository;
        this.creditCardRepository = creditCardRepository;
        this.accountHolderRepository = accountHolderRepository;
        AccountService.thirdPartyRepository = thirdPartyRepository;
    }
    //methods
    public static void sendMoneyToThirdParty(String hashedKey, String username, int amount, String accountId, String accountSecretKey) {
        if (thirdPartyRepository.findByHashedKey(hashedKey).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Hashed Key");
        }
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account doesn't exist.");
        }
        Account account = accountRepository.findById(accountId).get();
        if (!account.getPrimaryOwner().getUsername().equals(username) ||
                !account.getSecondaryOwner().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account doesn't belong to you.");
        }
        if (!account.getSecretKey().equals(accountSecretKey)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account doesn't exist and/or the secret key is wrong.");
        }
        account.setBalance(new Money(account.getBalance().decreaseAmount(BigDecimal.valueOf(amount))));
    }

    public List<Object[]> checkBalance(String username) {
        if (accountHolderRepository.findByUsername(username).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account/s doesn't exist.");
        }
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).get();
        return accountRepository.checkBalance(accountHolder);
    }

    public List<Object[]> myAccount(String username) {
        if (accountHolderRepository.findByUsername(username).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account/s doesn't exist or is/are not related to you.");
        }
        AccountHolder accountHolder = accountHolderRepository.findByUsername(username).get();
        List<Account> primaryAccounts = accountHolder.getPrimaryAccounts();
        List<Account> secondaryAccounts = accountHolder.getSecondaryAccounts();
        applyInterestOrMaintenance(primaryAccounts, secondaryAccounts);
        return accountRepository.checkBalance(accountHolder);
    }

    public List<Object[]> allAccounts() {
        return accountRepository.allAccounts();
    }

    public Account createAccount(AccountDTO accountDTO, Long accountHolderId) {
        if (accountHolderRepository.findById(accountHolderId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This account holder ID is not attached to any of our clients.");
        }
        AccountHolder accountHolder = accountHolderRepository.findById(accountHolderId).get();
        LocalDate dateOfBirth = accountHolder.getDateOfBirth();
        switch (accountDTO.getAccountType()) {
            case "checking" -> {
                if (LocalDate.now().getYear() - dateOfBirth.getYear() <= 24) {
                    StudentChecking account = createStudentsCheckingAccount(accountDTO, accountHolder);
                    studentCheckingRepository.save(account);
                    return account;
                }
                Checking account = createCheckingAccount(accountDTO, accountHolder);
                checkingRepository.save(account);
                return account;

            }
            case "savings" -> {
                Savings account = createSavingsAccount(accountDTO, accountHolder);
                savingsRepository.save(account);
                return account;
            }
            case "creditcard" -> {
                CreditCard account = createCreditCardAccount(accountDTO, accountHolder);
                creditCardRepository.save(account);
                return account;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Introduce a valid type of account in all lowercase.");
    }

    public void transfer(UserDetails userDetails, TransferInfo transferInfo) {
        AccountHolder accountHolder = retrieveAccountHolder(userDetails.getUsername(), "This username is not attached to any client of this bank.");
        Account originAccount = retrieveAccount(transferInfo.getOriginAccountId(), "The origin account number is wrong or invalid.");

        if (!originAccount.getPrimaryOwner().equals(accountHolder)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "The origin account is not attached to you or you are not the primary owner.");
        }

        if(accountRepository.findById(transferInfo.getDestinyAccountId()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The destiny account doesn't exist.");
        }
        Account destinyAccount = retrieveAccount(transferInfo.getDestinyAccountId(), "The destiny account doesn't exist.");
        String destinyPrimaryOwnerFName = destinyAccount.getPrimaryOwner().getFirstName();
        String destinyPrimaryOwnerLName = destinyAccount.getPrimaryOwner().getLastName();
        String destinySecondaryOwnerFName = destinyAccount.getSecondaryOwner().getFirstName();
        String destinySecondaryOwnerLName = destinyAccount.getSecondaryOwner().getLastName();

        if (!destinyPrimaryOwnerFName.equals(transferInfo.getFirstName())
                || !destinyPrimaryOwnerLName.equals(transferInfo.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The destiny account doesn't exist or it's not attached to this person.");
        }

        if (!destinySecondaryOwnerFName.equals(transferInfo.getFirstName())
                || !destinySecondaryOwnerLName.equals(transferInfo.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The destiny account doesn't exist or it's not attached to this person.");
        }

        BigDecimal originAccountBalance = originAccount.getBalance().getAmount();

        if (originAccountBalance.compareTo(transferInfo.getAmount()) < 0) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "The balance in your origin account is not enough to fulfill that operation.");
        }

        originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(transferInfo.getAmount())));
        destinyAccount.setBalance(new Money(destinyAccount.getBalance().increaseAmount(transferInfo.getAmount())));

        if (originAccount instanceof Checking || originAccount instanceof Savings) {
            if (originAccount.getBalance().getAmount().compareTo(((Checking) originAccount).getMinimumBalance().getAmount()) < 0) {
                ((Checking) originAccount).applyPenaltyFee();
            } else if (originAccount.getBalance().getAmount().compareTo(((Savings) originAccount).getMinimumBalance().getAmount()) < 0) {
                ((Savings) originAccount).applyPenaltyFee();
            }
        }
    }

    public Account modifyBalance(String accountId, BigDecimal newBalance) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account with that ID.");
        }
        Account account = accountRepository.findById(accountId).get();
        account.setBalance(new Money(newBalance));
        return account;
    }

    public void deleteAccount(String accountId) {
        if (accountRepository.findById(accountId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account with that ID.");
        }
        Account account = accountRepository.findById(accountId).get();
        accountRepository.delete(account);
    }

    //auxiliary methods
    public void applyInterestOrMaintenance(List<Account> primaryAccounts, List<Account> secondaryAccounts) {
        for (Account primaryAccount : primaryAccounts) {
            if (primaryAccount instanceof Savings) {
                ((Savings) primaryAccount).applyInterest();
            } else if (primaryAccount instanceof CreditCard) {
                ((CreditCard) primaryAccount).applyInterest();
            } else if (primaryAccount instanceof Checking) {
                ((Checking) primaryAccount).applyMaintenanceFee();
            }
        }
        for (Account secondaryAccount : secondaryAccounts) {
            if (secondaryAccount instanceof Savings) {
                ((Savings) secondaryAccount).applyInterest();
            } else if (secondaryAccount instanceof CreditCard) {
                ((CreditCard) secondaryAccount).applyInterest();
            } else if (secondaryAccount instanceof Checking) {
                ((Checking) secondaryAccount).applyMaintenanceFee();
            }
        }

    }

    public Checking createCheckingAccount (AccountDTO accountDTO, AccountHolder accountHolder) {
        return new Checking(
                accountDTO.getId(),
                accountDTO.getBalance(),
                accountDTO.getSecretKey(),
                accountHolder,
                LocalDate.now(),
                true
                );
    }

    public StudentChecking createStudentsCheckingAccount (AccountDTO accountDTO, AccountHolder accountHolder) {
        return new StudentChecking(
                accountDTO.getId(),
                accountDTO.getBalance(),
                accountDTO.getSecretKey(),
                accountHolder,
                LocalDate.now(),
                true
        );
    }

    public Savings createSavingsAccount (AccountDTO accountDTO, AccountHolder accountHolder) {
        return new Savings(
                accountDTO.getId(),
                accountDTO.getBalance(),
                accountDTO.getSecretKey(),
                accountHolder,
                LocalDate.now(),
                true,
                accountDTO.getMinimumBalance(),
                accountDTO.getInterestRate()
        );
    }

    public CreditCard createCreditCardAccount (AccountDTO accountDTO, AccountHolder accountHolder) {
        return new CreditCard(
                accountDTO.getId(),
                accountDTO.getBalance(),
                accountDTO.getSecretKey(),
                accountHolder,
                LocalDate.now(),
                true,
                accountDTO.getCreditLimit(),
                accountDTO.getInterestRate()
        );
    }

    public AccountHolder retrieveAccountHolder(String username, String errorMessage) {
        return accountHolderRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage));
    }

    public Account retrieveAccount(String accountId, String errorMessage) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage));
    }
}