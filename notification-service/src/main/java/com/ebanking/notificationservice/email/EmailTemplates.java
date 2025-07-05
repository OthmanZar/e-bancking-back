package com.ebanking.notificationservice.email;


import lombok.Getter;

public enum EmailTemplates {
    WITHDRAW_NOTIFICATION("withdraw-confirmation.html", "Withdrawal Confirmation"),
    TRANSFER_NOTIFICATION("transfer-confirmation.html", "Transfer Confirmation"),
    DEPOSIT_NOTIFICATION("deposit-confirmation.html", "Deposit Confirmation"),
    CREATE_ACCOUNT_NOTIFICATION("client-confirmation.html", "Client Account Created Successfully"),
    CREATE_BANK_ACCOUNT_NOTIFICATION("bankaccount-confirmation.html", "Bank Account Created Successfully"),
    CREATE_CARD_NOTIFICATION("card-confirmation.html", "Card Issued Successfully");
    ;


    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
