package com.ebanking.bankaccountservice.repositories;

import com.ebanking.bankaccountservice.entities.BankAccount;
import com.ebanking.bankaccountservice.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {


    List<BankAccount> findAllByClientId(Long clientId);
}
