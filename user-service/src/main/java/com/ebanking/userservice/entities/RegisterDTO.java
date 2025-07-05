package com.ebanking.userservice.entities;

import com.ebanking.userservice.enums.Sexe;
import com.ebanking.userservice.enums.TypeUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Sexe sexe;
    private String nationalID;
    private LocalDate birthday;
    private String address;
    private TypeUser userType;
    private String password;

}
