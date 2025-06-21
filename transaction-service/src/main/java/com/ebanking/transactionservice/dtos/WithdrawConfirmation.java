package com.ebanking.transactionservice.dtos;


import com.ebanking.transactionservice.enums.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawConfirmation(
        String cardNumber,
        BigDecimal amount,
        LocalDateTime transactionDate,
        TransactionStatus status,
        String atmCode,
        String destinationEmail


) {
}
