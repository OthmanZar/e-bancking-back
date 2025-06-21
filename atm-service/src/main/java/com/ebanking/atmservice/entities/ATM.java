package com.ebanking.atmservice.entities;

import com.ebanking.atmservice.enums.ATMStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String reference;

    @Column(nullable = false,unique = true)
    private String atmCode;

    private String city;

    private String location;

    private ATMStatus status;

    @Column(nullable = false)
    private BigDecimal maxWithdrawLimit;
}
