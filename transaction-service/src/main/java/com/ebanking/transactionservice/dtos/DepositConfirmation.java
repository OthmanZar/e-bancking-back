package com.ebanking.transactionservice.dtos;

import com.ebanking.transactionservice.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DepositConfirmation(

        String to_accountNumber,
        BigDecimal amount,
        LocalDateTime dateTime,
        TransactionStatus status,
        String fromDestinationEmail,
        String toDestinationEmail
) {
}
