package com.ebanking.transactionservice.repositories;

import com.ebanking.transactionservice.entities.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    @Query("SELECT AVG(t.amount) FROM Transfer t")
    Double getAverageTransferAmount();
    List<Deposit> findAllBySourceAccountReferenceOrTargetAccountReference(Long sourceAccountReference, Long targetAccountReference);
}
