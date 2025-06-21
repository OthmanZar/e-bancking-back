package com.ebanking.transactionservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Withdrawal extends Transaction{
    @Column(nullable = false)
    private Long cardId;

    @Column(nullable = false)
    private String  referenceATM;

}
