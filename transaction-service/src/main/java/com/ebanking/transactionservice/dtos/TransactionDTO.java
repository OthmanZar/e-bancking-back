package com.ebanking.transactionservice.dtos;

import com.ebanking.transactionservice.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(
        String reference,
        BigDecimal amount,
        LocalDateTime transactionDate,
        Long targetAccountReference,
        Long sourceAccountReference,
        String referenceATM,
        TransactionType type
) {
}
