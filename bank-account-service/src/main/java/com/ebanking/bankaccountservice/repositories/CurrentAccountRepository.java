package com.ebanking.bankaccountservice.repositories;

import com.ebanking.bankaccountservice.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount,Long> {

    boolean existsCurrentAccountByAccountNumber(String number);
}
