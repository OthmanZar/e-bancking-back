package com.ebanking.transactionservice.dtos;

import com.ebanking.transactionservice.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferResponseDTO(
        Long fromAccount,
        Long toAccount,
        BigDecimal amount,
        String reason,
        LocalDateTime dateTime,
        TransactionStatus status
) {
}
