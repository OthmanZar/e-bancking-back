package com.ebanking.transactionservice.dtos;

import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record WithdrawalRequestDTO (
        String cardNumber,
        BigDecimal amount,
        String atmCode
){
}
