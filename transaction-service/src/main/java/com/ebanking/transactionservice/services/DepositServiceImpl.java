package com.ebanking.transactionservice.services;

import com.ebanking.transactionservice.clients.BankAccountClient;
import com.ebanking.transactionservice.dtos.*;
import com.ebanking.transactionservice.entities.Deposit;
import com.ebanking.transactionservice.entities.Transaction;
import com.ebanking.transactionservice.enums.TransactionStatus;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import com.ebanking.transactionservice.kafka.NotificationProducer;
import com.ebanking.transactionservice.mappers.DepositMapper;
import com.ebanking.transactionservice.repositories.DepositRepository;
import com.ebanking.transactionservice.repositories.TransactionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements IDepositService {
    private final DepositRepository depositRepository;
    private final BankAccountClient bankAccountClient;
    private final DepositMapper depositMapper;
    private final NotificationProducer notificationProducer;
    private final HttpServletRequest request;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    @Override
    public DepositResponseDTO createDeposit(DepositRequestDTO depositRequestDTO) throws BankAccountNotFound {
        Deposit deposit = new Deposit();
        CurrentResponseDTO account =
                bankAccountClient.getCurrentAccount(depositRequestDTO.toAccount()).orElseThrow(
                        () -> new BankAccountNotFound("Current Account Not Found")
                );

        BigDecimal toNewBalance=account.balance().add(depositRequestDTO.amount());

        bankAccountClient.updateCurrentAccount(
                new UpdateCurrentAccount(account.accountNumber(),toNewBalance));

        deposit.setReference(UUID.randomUUID().toString());
        deposit.setAmount(depositRequestDTO.amount());
        deposit.setTargetAccountReference(account.id());
        deposit.setSourceAccountReference(account.id());
        deposit.setTransactionDate(LocalDateTime.now());
        deposit.setStatus(TransactionStatus.COMPLETED);
        deposit.setFraudSuspected(false);
        Deposit save = depositRepository.save(deposit);


        notificationProducer.sendNotificationDeposit(new DepositConfirmation(
                account.accountNumber(),
                save.getAmount(),
                save.getTransactionDate(),
                save.getStatus(),
                account.email(),
                account.email()
        ));

        System.out.println(request.getHeader("X-Real-IP") + "channel : "+ request.getHeader("X-Channel"));


        Transaction lastTransfer = transactionRepository.findTopByOrderByTransactionDateDesc();
        long secondsSinceLastTransfer = ChronoUnit.SECONDS.between(
                lastTransfer.getTransactionDate(),
                save.getTransactionDate()
        );

        transactionService.sendTransactionToProcessing(
                new TransactionNotification(
                        "U"+account.accountNumber(),
                        "D"+account.accountNumber(),
                        save.getId().intValue(),
                        save.getAmount(),
                        account.balance(),
                        toNewBalance,
                        account.balance(),
                        toNewBalance,
                        depositRepository.getAverageTransferAmount(),
                        secondsSinceLastTransfer,
                        "",
                        request.getHeader("X-Channel"),
                        "deposit",
                        save.getTransactionDate().toString(),
                        request.getHeader("X-Real-IP")

                )
        );
        return depositMapper.toDepositResponseDTO(save);
    }
}
