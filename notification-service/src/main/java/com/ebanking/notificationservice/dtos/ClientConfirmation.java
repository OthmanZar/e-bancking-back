package com.ebanking.notificationservice.dtos;


import com.ebanking.notificationservice.enums.Sexe;

import java.time.LocalDate;

public record ClientConfirmation(
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
