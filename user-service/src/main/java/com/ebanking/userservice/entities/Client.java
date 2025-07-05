package com.ebanking.userservice.entities;

import com.ebanking.userservice.enums.TypeUser;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.usertype.UserType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client extends User {

    private Boolean isVerified;
    private String nationalID;
    private LocalDate birthday;
    private String address;
    private TypeUser userType;
}
