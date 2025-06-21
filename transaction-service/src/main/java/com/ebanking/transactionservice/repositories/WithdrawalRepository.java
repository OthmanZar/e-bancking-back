package com.ebanking.transactionservice.repositories;

import com.ebanking.transactionservice.entities.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {

}
