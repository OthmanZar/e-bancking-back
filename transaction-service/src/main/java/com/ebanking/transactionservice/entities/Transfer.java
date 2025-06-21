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
public class Transfer extends Transaction {
    @Column(nullable = false)
    private Long targetAccountReference;

    private String reason; //motif

}
