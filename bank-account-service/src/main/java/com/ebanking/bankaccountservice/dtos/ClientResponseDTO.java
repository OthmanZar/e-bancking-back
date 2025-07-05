package com.ebanking.bankaccountservice.dtos;


import com.ebanking.bankaccountservice.enums.Sexe;
import com.ebanking.bankaccountservice.enums.TypeUser;

import java.time.LocalDate;

public record ClientResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Sexe sexe,
        String nationalID,
        LocalDate birthday,
        String address,
        TypeUser userType,
        String imageUrl

) {
}
