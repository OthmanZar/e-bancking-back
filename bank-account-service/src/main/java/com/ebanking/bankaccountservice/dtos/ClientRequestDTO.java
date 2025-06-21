package com.ebanking.bankaccountservice.dtos;


import com.ebanking.bankaccountservice.enums.Sexe;

import java.time.LocalDate;

public record ClientRequestDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Sexe sexe,
        String nationalID,
        LocalDate birthday,
        String address

) {
}
