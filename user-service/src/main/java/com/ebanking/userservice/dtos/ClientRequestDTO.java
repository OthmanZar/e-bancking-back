package com.ebanking.userservice.dtos;

import com.ebanking.userservice.enums.Sexe;

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
