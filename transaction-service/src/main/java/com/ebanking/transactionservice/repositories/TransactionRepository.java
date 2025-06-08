package com.ebanking.transactionservice.repositories;

import com.ebanking.transactionservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
