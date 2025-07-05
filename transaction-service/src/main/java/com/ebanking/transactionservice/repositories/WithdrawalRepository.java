package com.ebanking.transactionservice.repositories;

import com.ebanking.transactionservice.entities.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

    @Query("SELECT AVG(t.amount) FROM Transfer t")
    Double getAverageTransferAmount();
    List<Withdrawal> findWithdrawalBySourceAccountReference(Long sourceAccountReference);
}
