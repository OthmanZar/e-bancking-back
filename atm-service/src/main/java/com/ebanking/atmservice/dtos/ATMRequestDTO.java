package com.ebanking.atmservice.dtos;

import java.math.BigDecimal;

public record ATMRequestDTO(

        String city,
        String Location,
        BigDecimal maxWithdrawal
) {
}
