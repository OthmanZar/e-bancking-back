package com.ebanking.transactionservice.services;

import com.ebanking.transactionservice.clients.BankAccountClient;
import com.ebanking.transactionservice.clients.CardClient;
import com.ebanking.transactionservice.dtos.*;
import com.ebanking.transactionservice.entities.Withdrawal;
import com.ebanking.transactionservice.enums.CardStatus;
import com.ebanking.transactionservice.enums.TransactionStatus;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import com.ebanking.transactionservice.kafka.NotificationProducer;
import com.ebanking.transactionservice.mappers.WithdrawalMapper;
import com.ebanking.transactionservice.repositories.WithdrawalRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WithdrawalServiceImpl implements IWithdrawalService {
    private final WithdrawalRepository withdrawalRepository;
    private final CardClient cardClient;
    private final BankAccountClient bankAccountClient;
    private final WithdrawalMapper withdrawalMapper;
    private final NotificationProducer notificationProducer;
    @Transactional
    @Override
    public WithdrawalResponseDTO withdrawal(WithdrawalRequestDTO withdrawalRequestDTO) throws BadRequestException, BankAccountNotFound {
        CardResponseDTO cardResponseDTO = cardClient.getCardByCardNumber(withdrawalRequestDTO.cardNumber()).orElseThrow(() ->
                new BankAccountNotFound("Card not found"));

        if(cardResponseDTO.status().equals(CardStatus.BLOCKED)){
            throw new BadRequestException("Card is blocked");
        } else if (cardResponseDTO.status().equals(CardStatus.EXPIRED) ) {
            throw new BankAccountNotFound("Card is expired");
        }
        if(!cardResponseDTO.expiryDate().isAfter(LocalDate.now())){
            cardClient.updateCard(new UpdateCardDTO(cardResponseDTO.id(),CardStatus.EXPIRED));
            throw new BankAccountNotFound("Card is expired");
        }

        CurrentResponseDTO currentResponseDTO = bankAccountClient.getCurrentAccountByID(cardResponseDTO.currentAccountId()).orElseThrow(() ->
                new BankAccountNotFound("Account not found"));

        int compared = currentResponseDTO.balance().compareTo(withdrawalRequestDTO.amount());

        if(compared >= 0){
            BigDecimal postTransactionAmount =currentResponseDTO.balance().subtract(withdrawalRequestDTO.amount());
            bankAccountClient.updateCurrentAccount(
                    new UpdateCurrentAccount(
                            currentResponseDTO.accountNumber(),
                            postTransactionAmount));
            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setAmount(postTransactionAmount);
            withdrawal.setStatus(TransactionStatus.COMPLETED);
            withdrawal.setTransactionDate(LocalDateTime.now());
            withdrawal.setCardId(cardResponseDTO.id());
            withdrawal.setSourceAccountReference(currentResponseDTO.id());
            withdrawal.setFraudSuspected(false);
            withdrawal.setReference(UUID.randomUUID().toString());
            withdrawal.setReferenceATM(withdrawalRequestDTO.atmCode());
            Withdrawal save = withdrawalRepository.save(withdrawal);

            notificationProducer.sendNotificationWithdraw(new WithdrawConfirmation(
                    cardResponseDTO.cardNumber(),
                    withdrawalRequestDTO.amount(),
                    save.getTransactionDate(),
                    save.getStatus(),
                    withdrawalRequestDTO.atmCode(),
                    currentResponseDTO.email()

            ));

            return withdrawalMapper.toWithdrawalResponseDTO(save);
        }else{

            throw  new BankAccountNotFound("Yous Don't have enough funds to withdraw.");
        }

    }
}
