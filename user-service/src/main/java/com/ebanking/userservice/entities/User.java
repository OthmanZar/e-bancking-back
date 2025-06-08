package com.ebanking.userservice.entities;

import com.ebanking.userservice.enums.Sexe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Sexe sexe;

}
