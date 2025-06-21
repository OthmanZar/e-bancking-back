package com.ebanking.cardservice.entities;

import com.ebanking.cardservice.enums.CardStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true , length = 20)
    private String cardNumber;

    @Column(nullable = false , unique = true , length = 16)
    private Integer cvv;

    @Column(nullable = false)
    @Future
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(nullable = false)
    private Long currentAccountId;
}
