package com.ebanking.transactionservice.entities;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Deposit extends Transaction{
    private Long targetAccountReference;
}
