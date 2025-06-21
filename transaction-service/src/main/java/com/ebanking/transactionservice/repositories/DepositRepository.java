package com.ebanking.transactionservice.repositories;

import com.ebanking.transactionservice.entities.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
