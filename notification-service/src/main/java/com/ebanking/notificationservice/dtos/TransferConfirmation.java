package com.ebanking.notificationservice.dtos;


import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferConfirmation(
        String from_accountNumber,
        String to_accountNumber,
        BigDecimal amount,
        String reason,
        LocalDateTime dateTime,
        String status,
        String fromDestinationEmail,
        String toDestinationEmail
) {
}
