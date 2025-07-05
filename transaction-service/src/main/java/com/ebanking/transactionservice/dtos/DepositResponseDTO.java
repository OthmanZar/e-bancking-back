package com.ebanking.transactionservice.dtos;

import com.ebanking.transactionservice.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DepositResponseDTO(
        Long toAccount,
        BigDecimal amount,
        LocalDateTime dateTime,
        TransactionStatus status
) {
}
