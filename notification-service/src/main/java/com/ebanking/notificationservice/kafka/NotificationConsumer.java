package com.ebanking.notificationservice.kafka;

import com.ebanking.notificationservice.dtos.*;
import com.ebanking.notificationservice.email.EmailService;
import com.ebanking.notificationservice.entities.Notification;
import com.ebanking.notificationservice.enums.NotificationType;
import com.ebanking.notificationservice.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    @KafkaListener(topics = "withdraw-topic")
    public void consumeWithdrawNotification(WithdrawConfirmation withdrawConfirmation){
        log.info(String.format("Message from topic withdraw-ms : %s",withdrawConfirmation));
        notificationRepository.save(Notification.builder()
                .type(NotificationType.WITHDRAW_NOTIFICATION)
                .notificationDate(LocalDateTime.now())
                .withdrawConfirmation(withdrawConfirmation)
                .build());

        emailService.sendWithdrawSuccessEmail(withdrawConfirmation);
    }

    @KafkaListener(topics = "transfer-topic")
    public void consumeTransferNotification(TransferConfirmation transferConfirmation){
        log.info(String.format("Message from topic transfer-ms : %s",transferConfirmation));
        notificationRepository.save(Notification.builder()
                .type(NotificationType.TRANSFER_NOTIFICATION)
                .notificationDate(LocalDateTime.now())
                .transferConfirmation(transferConfirmation)
                .build());

        emailService.sendTransferSuccessEmail(transferConfirmation);
    }

    @KafkaListener(topics = "deposit-topic")
    public void consumeDepositNotification(DepositConfirmation depositConfirmation){
        log.info(String.format("Message from topic deposit-ms : %s",depositConfirmation));
        notificationRepository.save(Notification.builder()
                .type(NotificationType.DEPOSIT_NOTIFICATION)
                .notificationDate(LocalDateTime.now())
                .depositConfirmation(depositConfirmation)
                .build());
    }

    @KafkaListener(topics = "bank-account-topic")
    public void consumeBankNotification(BankAccountConfirmation bankAccountConfirmation){
        log.info(String.format("Message from topic bank-account-ms : %s",bankAccountConfirmation));
        notificationRepository.save(Notification.builder()
                .type(NotificationType.CREATE_BANK_ACCOUNT_NOTIFICATION)
                .notificationDate(LocalDateTime.now())
                .bankAccountConfirmation(bankAccountConfirmation)
                .build());

        emailService.sendBankAccountEmail(bankAccountConfirmation);
    }

    @KafkaListener(topics = "client-topic")
    public void consumeClientNotification(ClientConfirmation clientConfirmation){
        log.info(String.format("Message from topic client-ms : %s",clientConfirmation));
        notificationRepository.save(Notification.builder()
                .type(NotificationType.CREATE_ACCOUNT_NOTIFICATION)
                .notificationDate(LocalDateTime.now())
                .clientConfirmation(clientConfirmation)
                .build());

        emailService.sendClientCreationEmail(clientConfirmation);
    }

    @KafkaListener(topics = "card-topic")
    public void consumeCardNotification(CardConfirmation cardConfirmation){
        log.info(String.format("Message from topic card-ms : %s",cardConfirmation));
        notificationRepository.save(Notification.builder()
                .type(NotificationType.CREATE_CARD_NOTIFICATION)
                .notificationDate(LocalDateTime.now())
                .cardConfirmation(cardConfirmation)
                .build());

        emailService.sendCardCreationEmail(cardConfirmation);
    }

}
