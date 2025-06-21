package com.ebanking.notificationservice.entities;

import com.ebanking.notificationservice.dtos.*;
import com.ebanking.notificationservice.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationDate;
    private WithdrawConfirmation withdrawConfirmation;
    private TransferConfirmation transferConfirmation;
    private DepositConfirmation depositConfirmation;
    private BankAccountConfirmation bankAccountConfirmation;
    private CardConfirmation cardConfirmation;
    private ClientConfirmation clientConfirmation;
}
