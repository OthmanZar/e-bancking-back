package com.ebanking.bankaccountservice.services;

import com.ebanking.bankaccountservice.exceptions.BankAccountNotFound;

import java.math.BigDecimal;

public interface ICurrentAccountService {
    void createCurrentAccount(Long clientID);

    BigDecimal getAmountOfCurrentAccount(Long id) throws BankAccountNotFound;

    void deleteCurrentAccount(Long id) throws BankAccountNotFound;

}
