package com.ebanking.transactionservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Deposit extends Transaction{
    @Column(nullable = false)
    private Long targetAccountReference;
}
