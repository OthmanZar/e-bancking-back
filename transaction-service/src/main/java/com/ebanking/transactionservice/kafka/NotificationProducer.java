package com.ebanking.transactionservice.kafka;

import com.ebanking.transactionservice.dtos.DepositConfirmation;
import com.ebanking.transactionservice.dtos.TransferConfirmation;
import com.ebanking.transactionservice.dtos.WithdrawConfirmation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationProducer {
    private final KafkaTemplate<String, WithdrawConfirmation> kafkaTemplateWithdraw;
    private final KafkaTemplate<String, TransferConfirmation> kafkaTemplateTransfer;
    private final KafkaTemplate<String, DepositConfirmation> kafkaTemplateDeposit;


    public void sendNotificationWithdraw(WithdrawConfirmation request){
        log.info("Sending Notification with body <{}>",request);

        Message<WithdrawConfirmation> message = MessageBuilder.
                withPayload(request).
                setHeader(KafkaHeaders.TOPIC,"withdraw-topic")
                .build();
        kafkaTemplateWithdraw.send(message);
    }

    public void sendNotificationTransfer(TransferConfirmation request){
        log.info("Sending Notification with body <{}>",request);

        Message<TransferConfirmation> message = MessageBuilder.
                withPayload(request).
                setHeader(KafkaHeaders.TOPIC,"transfer-topic")
                .build();
        kafkaTemplateTransfer.send(message);
    }

    public void sendNotificationDeposit(DepositConfirmation request){
        log.info("Sending Notification with body <{}>",request);

        Message<DepositConfirmation> message = MessageBuilder.
                withPayload(request).
                setHeader(KafkaHeaders.TOPIC,"deposit-topic")
                .build();
        kafkaTemplateDeposit.send(message);
    }

}
