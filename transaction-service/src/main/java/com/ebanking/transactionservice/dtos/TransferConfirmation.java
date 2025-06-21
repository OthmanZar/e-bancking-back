package com.ebanking.transactionservice.dtos;

import com.ebanking.transactionservice.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferConfirmation(
        String from_accountNumber,
        String to_accountNumber,
        BigDecimal amount,
        String reason,
        LocalDateTime dateTime,
        TransactionStatus status,
        String fromDestinationEmail,
        String toDestinationEmail
) {
}
