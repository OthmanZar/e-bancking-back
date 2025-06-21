package com.ebanking.bankaccountservice.repositories;

import com.ebanking.bankaccountservice.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount,Long> {

    boolean existsCurrentAccountByAccountNumber(String number);

    Optional<CurrentAccount> findCurrentAccountByAccountNumber(String number);
}
