package com.ebanking.transactionservice.repositories;

import com.ebanking.transactionservice.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    @Query("SELECT AVG(t.amount) FROM Transfer t")
    Double getAverageTransferAmount();

    Transfer findTopByOrderByTransactionDateDesc();

    List<Transfer> findAllBySourceAccountReferenceOrTargetAccountReference(Long accountId1, Long accountId2);

}
