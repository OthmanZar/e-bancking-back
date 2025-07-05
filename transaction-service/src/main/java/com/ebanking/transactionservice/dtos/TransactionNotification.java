package com.ebanking.transactionservice.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionNotification(
         String origin_user_id,
         String destination_user_id,
         Integer transaction_sequence_id,
         BigDecimal amount,
         BigDecimal origin_old_balance,
         BigDecimal origin_new_balance,
         BigDecimal dest_old_balance,
         BigDecimal dest_new_balance,
         Double avg_transaction_amount,
         long time_since_last_tx,
         String country,
         String channel,
         String transaction_type,
         String timestamp,
         String ip_address

) {

}
