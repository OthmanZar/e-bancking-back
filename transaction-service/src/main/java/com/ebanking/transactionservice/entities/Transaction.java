package com.ebanking.transactionservice.entities;

import com.ebanking.transactionservice.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private Long sourceAccountReference;

    private BigDecimal amount;

    private LocalDateTime transactionDate;

    private TransactionStatus status;

    private Boolean fraudSuspected;

}
