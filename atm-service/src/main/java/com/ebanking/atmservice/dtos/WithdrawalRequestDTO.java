package com.ebanking.atmservice.dtos;

import java.math.BigDecimal;

public record WithdrawalRequestDTO(
        String cardNumber,
        BigDecimal amount,
        String atmCode
){
}
