package com.ebanking.atmservice.dtos;


import com.ebanking.atmservice.enums.TransactionStatus;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawalResponseDTO(
        Long cardID,
        BigDecimal amount,
        BigDecimal soldAfter,
        LocalDateTime transactionDate,
        TransactionStatus status,
        String atmCode
) {
}
