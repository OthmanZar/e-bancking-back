package com.ebanking.atmservice.repositories;

import com.ebanking.atmservice.entities.ATM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ATMRepository extends JpaRepository<ATM, Long> {

    int countATMSByCity(String city);

    Optional<ATM> findByReference(String reference);

    Optional<ATM> findByAtmCode(String reference);

}
