package com.ebanking.cardservice.repositories;

import com.ebanking.cardservice.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Long> {

    boolean existsByCardNumber(String cardNumber);

    boolean existsByCvv(Integer cvv);


    Optional<Card> findByCardNumber(String cardNumber);
}
