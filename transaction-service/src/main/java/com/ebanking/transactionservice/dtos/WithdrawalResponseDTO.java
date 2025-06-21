package com.ebanking.transactionservice.dtos;

import com.ebanking.transactionservice.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawalResponseDTO(
        Long cardID,
        BigDecimal amount,
        LocalDateTime transactionDate,
        TransactionStatus status

) {
}
