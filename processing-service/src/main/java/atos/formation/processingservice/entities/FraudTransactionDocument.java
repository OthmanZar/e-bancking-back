package atos.formation.processingservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.math.BigDecimal;

@Document(indexName = "fraud-transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FraudTransactionDocument {

    @Id
    private String id;

    @Field(name = "origin_user_id")
    private String originUserId;

    @Field(name = "destination_user_id")
    private String destinationUserId;

    @Field(name = "transaction_sequence_id")
    private Integer transactionSequenceId;

    @Field(name = "amount")
    private BigDecimal amount;

    @Field(name = "origin_old_balance")
    private BigDecimal originOldBalance;

    @Field(name = "origin_new_balance")
    private BigDecimal originNewBalance;

    @Field(name = "dest_old_balance")
    private BigDecimal destOldBalance;

    @Field(name = "dest_new_balance")
    private BigDecimal destNewBalance;

    @Field(name = "avg_transaction_amount")
    private Double avgTransactionAmount;

    @Field(name = "time_since_last_tx")
    private long timeSinceLastTx;

    @Field(name = "country")
    private String country;

    @Field(name = "channel")
    private String channel;

    @Field(name = "transaction_type")
    private String transactionType;

    @Field(name = "timestamp")
    private String timestamp;

    @Field(name = "ip_address")
    private String ipAddress;

    @Field(name = "is_fraud")
    private boolean isFraud;
}
