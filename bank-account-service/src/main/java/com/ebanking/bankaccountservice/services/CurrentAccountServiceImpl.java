package com.ebanking.bankaccountservice.services;

import com.ebanking.bankaccountservice.entities.CurrentAccount;
import com.ebanking.bankaccountservice.enums.AccountStatus;
import com.ebanking.bankaccountservice.exceptions.BankAccountNotFound;
import com.ebanking.bankaccountservice.repositories.CurrentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CurrentAccountServiceImpl implements ICurrentAccountService {

    private final CurrentAccountRepository currentAccountRepository;

    @Override
    public void createCurrentAccount(Long clientID) {
        String accountNumber = generateUniqueAccountNumber();
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccountNumber(accountNumber);
        currentAccount.setBalance(BigDecimal.ZERO);
        currentAccount.setStatus(AccountStatus.ACTIVE);
        currentAccount.setClientId(clientID);
        // add overdraft

        //create card
        currentAccountRepository.save(currentAccount);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        Random random = new Random();
        String prefix = "1102";

        do {
            long randomPart = Math.abs(random.nextLong()) % 1_0000_0000_0000L;
            String randomPartStr = String.format("%012d", randomPart);
            accountNumber = prefix + randomPartStr;
        } while (currentAccountRepository.existsCurrentAccountByAccountNumber(accountNumber));

        return accountNumber;
    }

    @Override
    public BigDecimal getAmountOfCurrentAccount(Long id) throws BankAccountNotFound {
        CurrentAccount currentAccount = currentAccountRepository.findById(id).orElseThrow(() ->
                new BankAccountNotFound("Current Account Not Found"));
        return currentAccount.getBalance();
    }

    @Override
    public void deleteCurrentAccount(Long id) throws BankAccountNotFound {
        CurrentAccount currentAccount = currentAccountRepository.findById(id).orElseThrow(() ->
                new BankAccountNotFound("Current Account Not Found"));

        currentAccountRepository.delete(currentAccount);
    }
}
