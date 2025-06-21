package com.ebanking.atmservice.dtos;

import com.ebanking.atmservice.enums.ATMStatus;

import java.math.BigDecimal;

public record ATMResponseDTO(
         String reference,

         String atmCode,

         String location,

         ATMStatus status,

         BigDecimal maxWithdrawLimit,

         String city
) {
}
