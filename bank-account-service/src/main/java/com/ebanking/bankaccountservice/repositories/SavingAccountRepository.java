package com.ebanking.bankaccountservice.repositories;

import com.ebanking.bankaccountservice.entities.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount,Long> {
}
