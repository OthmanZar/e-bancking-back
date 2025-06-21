package com.ebanking.bankaccountservice.services;

import com.ebanking.bankaccountservice.dtos.CurrentResponseDTO;
import com.ebanking.bankaccountservice.entities.CurrentAccount;
import com.ebanking.bankaccountservice.exceptions.BankAccountNotFound;

import java.math.BigDecimal;

public interface ICurrentAccountService {
    void createCurrentAccount(Long clientID,String email);

    BigDecimal getAmountOfCurrentAccount(Long id) throws BankAccountNotFound;

    void deleteCurrentAccount(Long id) throws BankAccountNotFound;

    CurrentResponseDTO getCurrentAccountByAccountNumber(String accountNumber) throws BankAccountNotFound;
    CurrentResponseDTO getCurrentAccountByID(Long id) throws BankAccountNotFound;

    CurrentResponseDTO updateCurrentAccountByAccountNumber(String accountNumber, BigDecimal amount) throws BankAccountNotFound;


}
