package com.ebanking.bankaccountservice.dtos;

import com.ebanking.bankaccountservice.enums.AccountStatus;

import java.math.BigDecimal;

public record CurrentResponseDTO (
        Long id,
       String accountNumber,
       BigDecimal balance,
       AccountStatus status,
        String email,
        String fullName
){
}
