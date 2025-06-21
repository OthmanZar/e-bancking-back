package com.ebanking.bankaccountservice.mappers;

import com.ebanking.bankaccountservice.client.AccountClient;
import com.ebanking.bankaccountservice.dtos.BankAccountConfirmation;
import com.ebanking.bankaccountservice.dtos.ClientRequestDTO;
import com.ebanking.bankaccountservice.dtos.CurrentResponseDTO;
import com.ebanking.bankaccountservice.entities.BankAccount;
import com.ebanking.bankaccountservice.entities.CurrentAccount;
import com.ebanking.bankaccountservice.entities.SavingAccount;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.ClientResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentAccountMapper {





    public CurrentResponseDTO toResponseDTO(CurrentAccount currentAccount, ClientRequestDTO clientResponse) {
        return new CurrentResponseDTO(
                currentAccount.getId(),
                currentAccount.getAccountNumber(),
                currentAccount.getBalance(),
                currentAccount.getStatus(),
                clientResponse.email(),
                clientResponse.firstName()+" "+clientResponse.lastName()


        );
    }

    public BankAccountConfirmation toConfirmation(BankAccount bankAccount,String destinationEmail) {
       String type= bankAccount instanceof CurrentAccount ? "current" : "saving";
       CurrentAccount currentAccount = type.equals("current")? (CurrentAccount) bankAccount : null;
        SavingAccount savingAccount = type.equals("saving")? (SavingAccount) bankAccount : null;
        return new BankAccountConfirmation(
               bankAccount.getAccountNumber(),
                type,
                bankAccount.getBalance(),
                bankAccount.getStatus(),
                currentAccount!=null?currentAccount.getOverdraft():null,
                savingAccount!=null?savingAccount.getInterestRate():null,
                destinationEmail


        );
    }
}
