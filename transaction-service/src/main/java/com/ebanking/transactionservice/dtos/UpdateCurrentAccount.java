package com.ebanking.transactionservice.dtos;

import java.math.BigDecimal;

public record UpdateCurrentAccount(
        String accountNumber,
        BigDecimal amount
) {
}
