package com.ebanking.userservice.dtos;

import com.ebanking.userservice.enums.Sexe;
import com.ebanking.userservice.enums.TypeUser;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record ClientRequestDTO(

        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Sexe sexe,
        String nationalID,
        LocalDate birthday,
        String address,
        TypeUser type,
        MultipartFile image

) {
}
