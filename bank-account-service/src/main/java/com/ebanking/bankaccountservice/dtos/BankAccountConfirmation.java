package com.ebanking.bankaccountservice.dtos;

import com.ebanking.bankaccountservice.enums.AccountStatus;

import java.math.BigDecimal;

public record BankAccountConfirmation(

        String accountNumber,
        String accountType,
        BigDecimal balance,
        AccountStatus status,
        BigDecimal overdraft,
        Double interestRate,
        String destinationEmail
) {
}
