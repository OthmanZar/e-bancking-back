package com.ebanking.transactionservice.services;

import com.ebanking.transactionservice.clients.BankAccountClient;
import com.ebanking.transactionservice.dtos.*;
import com.ebanking.transactionservice.entities.Transaction;
import com.ebanking.transactionservice.entities.Transfer;
import com.ebanking.transactionservice.enums.TransactionStatus;
import com.ebanking.transactionservice.exceptions.BankAccountNotFound;
import com.ebanking.transactionservice.kafka.NotificationProducer;
import com.ebanking.transactionservice.mappers.TransferMapper;
import com.ebanking.transactionservice.repositories.TransactionRepository;
import com.ebanking.transactionservice.repositories.TransferRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements ITransferService {

    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final BankAccountClient bankAccountClient;
    private final NotificationProducer notificationProducer;
    private final TransactionService transactionService;
    private final HttpServletRequest request;
    private final TransactionRepository transactionRepository;
    @Transactional
    @Override
    public TransferResponseDTO transfer(TransferRequestDTO transferRequestDTO) throws BankAccountNotFound {
        Transfer transfer = new Transfer();

        CurrentResponseDTO fromAccount =
                bankAccountClient.getCurrentAccount(transferRequestDTO.fromAccount()).orElseThrow(
                        () -> new BankAccountNotFound("Current Account Not Found")
                );

        CurrentResponseDTO toAccount =
                bankAccountClient.getCurrentAccount(transferRequestDTO.toAccount()).orElseThrow(
                        () -> new BankAccountNotFound("Target Account Not Found")
                );
        System.out.println(fromAccount.balance());
        System.out.println(transferRequestDTO.amount());
        int compared = fromAccount.balance().compareTo(transferRequestDTO.amount());
        BigDecimal fromNewBalance;
        BigDecimal toNewBalance;

        if(compared >= 0 ){
            fromNewBalance=fromAccount.balance().subtract(transferRequestDTO.amount());
            toNewBalance=toAccount.balance().add(transferRequestDTO.amount());
            bankAccountClient.updateCurrentAccount(
                    new UpdateCurrentAccount(fromAccount.accountNumber(),fromNewBalance));
            bankAccountClient.updateCurrentAccount(
                    new UpdateCurrentAccount(toAccount.accountNumber(),toNewBalance));

            transfer.setReference(UUID.randomUUID().toString());
            transfer.setReason(transferRequestDTO.reason());
            transfer.setTargetAccountReference(toAccount.id());
            transfer.setSourceAccountReference(fromAccount.id());
            transfer.setStatus(TransactionStatus.COMPLETED);//Check Later
            transfer.setTransactionDate(LocalDateTime.now());
            transfer.setFraudSuspected(false);
            transfer.setAmount(transferRequestDTO.amount());
        }else{
            throw new BankAccountNotFound("Yous Don't have enough funds to transfer.");
        }

        Transfer save = transferRepository.save(transfer);

        notificationProducer.sendNotificationTransfer(new TransferConfirmation(
                fromAccount.accountNumber(),
                toAccount.accountNumber(),
                transferRequestDTO.amount(),
                save.getReason(),
                save.getTransactionDate(),
                save.getStatus(),
                fromAccount.email(),
                toAccount.email()

        ));
        System.out.println(request.getHeader("X-Real-IP") + "channel : "+ request.getHeader("X-Channel"));

        Transaction lastTransfer = transactionRepository.findTopByOrderByTransactionDateDesc();
        long secondsSinceLastTransfer = ChronoUnit.SECONDS.between(
                lastTransfer.getTransactionDate(),
                save.getTransactionDate()
        );
        transactionService.sendTransactionToProcessing(
                new TransactionNotification(
                        "U"+fromAccount.accountNumber(),
                        "U"+toAccount.accountNumber(),
                        save.getId().intValue(),
                        transfer.getAmount(),
                        fromAccount.balance(),
                        fromNewBalance,
                        toAccount.balance(),
                        toNewBalance,
                        transferRepository.getAverageTransferAmount(),
                        secondsSinceLastTransfer,
                        "",
                        request.getHeader("X-Channel"),
                        "transfer",
                        save.getTransactionDate().toString(),
                        request.getHeader("X-Real-IP")

                )
        );
        return transferMapper.toTransactionResponse(save);
    }
}
