package com.ebanking.notificationservice.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record WithdrawConfirmation(
        String cardNumber,
        BigDecimal amount,
        LocalDateTime transactionDate,
        String status,
        String atmCode,
        String destinationEmail

) {
}
