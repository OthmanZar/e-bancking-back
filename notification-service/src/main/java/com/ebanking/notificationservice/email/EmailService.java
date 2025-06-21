package com.ebanking.notificationservice.email;

import com.ebanking.notificationservice.dtos.*;
import com.ebanking.notificationservice.enums.NotificationType;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.ebanking.notificationservice.email.EmailTemplates.WITHDRAW_NOTIFICATION;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendWithdrawSuccessEmail(WithdrawConfirmation dto) {
        Map<String, Object> vars = Map.of(
                "cardNumber", dto.cardNumber(),
                "amount", dto.amount(),
                "transactionDate", dto.transactionDate(),
                "status", dto.status(),
                "atmCode", dto.atmCode()
        );
        try {
            sendEmail(dto.destinationEmail(),
                    EmailTemplates.WITHDRAW_NOTIFICATION.getTemplate(),
                    EmailTemplates.WITHDRAW_NOTIFICATION.getSubject(),
                    vars);
        } catch (MessagingException e) {
            log.warn("❌ Failed to send Withdraw email to {}", dto.destinationEmail());
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendTransferSuccessEmail(TransferConfirmation dto) {
        Map<String, Object> vars = Map.of(
                "from_accountNumber", dto.from_accountNumber(),
                "to_accountNumber", dto.to_accountNumber(),
                "amount", dto.amount(),
                "reason", dto.reason(),
                "dateTime", dto.dateTime(),
                "status", dto.status()
        );

        try {
            // Send to the sender
            sendEmail(
                    dto.fromDestinationEmail(),
                    EmailTemplates.TRANSFER_NOTIFICATION.getTemplate(),
                    "Transfer Confirmation - You sent money",
                    vars
            );

            // Send to the receiver
            sendEmail(
                    dto.toDestinationEmail(),
                    EmailTemplates.TRANSFER_NOTIFICATION.getTemplate(),
                    "Transfer Confirmation - You received money",
                    vars
            );

        } catch (MessagingException | jakarta.mail.MessagingException e) {
            log.warn("❌ Failed to send Transfer email to {} or {}", dto.fromDestinationEmail(), dto.toDestinationEmail());
        }
    }

    @Async
    public void sendClientCreationEmail(ClientConfirmation dto) {
        Map<String, Object> vars = Map.of(
                "firstName", dto.firstName(),
                "lastName", dto.lastName(),
                "email", dto.email(),
                "phoneNumber", dto.phoneNumber(),
                "sexe", dto.sexe(),
                "nationalID", dto.nationalID(),
                "birthday", dto.birthday(),
                "address", dto.address()
        );
        try {
            sendEmail(dto.email(),
                    EmailTemplates.CREATE_ACCOUNT_NOTIFICATION.getTemplate(),
                    EmailTemplates.CREATE_ACCOUNT_NOTIFICATION.getSubject(),
                    vars);
        } catch (MessagingException | jakarta.mail.MessagingException e) {
            log.warn("❌ Failed to send Client Creation email to {}", dto.email());
        }
    }

    @Async
    public void sendBankAccountEmail(BankAccountConfirmation dto) {
        Map<String, Object> vars = new HashMap<>(Map.of(
                "accountNumber", dto.accountNumber(),
                "accountType", dto.accountType(),
                "balance", dto.balance(),
                "status", dto.status()
        ));
        if ("current".equalsIgnoreCase(dto.accountType())) {
            vars.put("overdraft", dto.overdraft());
        }

        if ("saving".equalsIgnoreCase(dto.accountType())) {
            vars.put("interestRate", dto.interestRate());
        }
        try {
            sendEmail(dto.destinationEmail(),
                    EmailTemplates.CREATE_BANK_ACCOUNT_NOTIFICATION.getTemplate(),
                    EmailTemplates.CREATE_BANK_ACCOUNT_NOTIFICATION.getSubject(),
                    vars);
        } catch (MessagingException e) {
            log.warn("❌ Failed to send Bank Account Creation email to {}", dto.destinationEmail());
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendCardCreationEmail(CardConfirmation dto) {
        Map<String, Object> vars = Map.of(
                "cardNumber", dto.cardNumber(),
                "cvv", dto.cvv(),
                "expiryDate", dto.expiryDate(),
                "status", dto.status()
        );
        try {
            sendEmail(dto.destinationEmail(),
                    EmailTemplates.CREATE_CARD_NOTIFICATION.getTemplate(),
                    EmailTemplates.CREATE_CARD_NOTIFICATION.getSubject(),
                    vars);
        } catch (MessagingException e) {
            log.warn("❌ Failed to send Card Creation email to {}", dto.destinationEmail());
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendEmail(String to, String templateName, String subject, Map<String, Object> variables) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("othmanzarrouk30@gmail.com");

        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(templateName, context);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(htmlContent, true);

        mailSender.send(mimeMessage);
        log.info("✅ Email sent to {} using template {}", to, templateName);
    }

}
