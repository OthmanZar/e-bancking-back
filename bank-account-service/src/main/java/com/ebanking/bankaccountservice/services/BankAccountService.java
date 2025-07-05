package com.ebanking.bankaccountservice.services;


import com.ebanking.bankaccountservice.client.AccountClient;
import com.ebanking.bankaccountservice.dtos.BankAccountConfirmation;
import com.ebanking.bankaccountservice.dtos.BankAccounts;
import com.ebanking.bankaccountservice.entities.BankAccount;
import com.ebanking.bankaccountservice.entities.CurrentAccount;
import com.ebanking.bankaccountservice.entities.SavingAccount;
import com.ebanking.bankaccountservice.repositories.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final AccountClient accountClient;
    public List<BankAccounts> getAllBankAccounts(Long id_client) {
        List<BankAccounts> bankAccountConfirmations = new ArrayList<>();
        List<BankAccount> allByClientId = bankAccountRepository.findAllByClientId(id_client);

       allByClientId.forEach(bankAccount -> bankAccountConfirmations.add(
               new BankAccounts(
                       bankAccount.getId(),
                       bankAccount.getAccountNumber(),
                       bankAccount instanceof CurrentAccount ? "current" : "saving",
                       bankAccount.getBalance(),
                       bankAccount.getStatus(),
                       bankAccount instanceof CurrentAccount ? (((CurrentAccount) bankAccount).getOverdraft()) : null,
                       bankAccount instanceof SavingAccount ? (((SavingAccount) bankAccount).getInterestRate()) : null

                       )
       ));

       return bankAccountConfirmations;
    }

}
