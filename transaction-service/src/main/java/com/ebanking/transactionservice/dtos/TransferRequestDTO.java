package com.ebanking.transactionservice.dtos;

import java.math.BigDecimal;

public record TransferRequestDTO (
        String fromAccount,
        String toAccount,
        BigDecimal amount,
        String reason
){
}
