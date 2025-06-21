package com.ebanking.notificationservice.dtos;


import com.ebanking.notificationservice.enums.AccountStatus;

import java.math.BigDecimal;

public record BankAccountConfirmation(

        String accountNumber,
        String accountType,
        BigDecimal balance,
        String status,
        BigDecimal overdraft,
        Double interestRate,
        String destinationEmail
) {
}
