package com.ebanking.transactionservice.repositories;

import com.ebanking.transactionservice.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
