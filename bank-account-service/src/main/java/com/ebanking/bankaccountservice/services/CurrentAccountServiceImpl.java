package com.ebanking.bankaccountservice.services;

import com.ebanking.bankaccountservice.client.AccountClient;
import com.ebanking.bankaccountservice.client.CardClient;

import com.ebanking.bankaccountservice.dtos.ClientResponseDTO;
import com.ebanking.bankaccountservice.dtos.CurrentResponseDTO;
import com.ebanking.bankaccountservice.entities.CurrentAccount;
import com.ebanking.bankaccountservice.enums.AccountStatus;
import com.ebanking.bankaccountservice.exceptions.BankAccountNotFound;
import com.ebanking.bankaccountservice.kafka.NotificationProducer;
import com.ebanking.bankaccountservice.mappers.CurrentAccountMapper;
import com.ebanking.bankaccountservice.repositories.CurrentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CurrentAccountServiceImpl implements ICurrentAccountService {

    private final CurrentAccountRepository currentAccountRepository;
    private final CardClient cardClient;
    private final CurrentAccountMapper currentAccountMapper;
    private final NotificationProducer notificationProducer;
    private final AccountClient accountClient;
    @Override
    public void createCurrentAccount(Long clientID,String email) {
        String accountNumber = generateUniqueAccountNumber();
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccountNumber(accountNumber);
        currentAccount.setBalance(BigDecimal.ZERO);
        currentAccount.setStatus(AccountStatus.ACTIVE);
        currentAccount.setClientId(clientID);
        currentAccount.setOverdraft(BigDecimal.ZERO);
        CurrentAccount save = currentAccountRepository.save(currentAccount);

        notificationProducer.sendNotification(currentAccountMapper.toConfirmation(save,email));

        cardClient.createCard(save.getId(),email);

    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        Random random = new Random();
        String prefix = "2003";

        do {
            long randomPart = Math.abs(random.nextLong()) % 1_0000_0000_0000L;
            String randomPartStr = String.format("%012d", randomPart);
            accountNumber = prefix + randomPartStr + "1102";
        } while (currentAccountRepository.existsCurrentAccountByAccountNumber(accountNumber));

        return accountNumber;
    }

    @Override
    public BigDecimal getAmountOfCurrentAccount(Long id) throws BankAccountNotFound {
        CurrentAccount currentAccount = currentAccountRepository.findById(id).orElseThrow(() ->
                new BankAccountNotFound("Current Account Not Found"));
        return currentAccount.getBalance();
    }

    @Override
    public void deleteCurrentAccount(Long id) throws BankAccountNotFound {
        CurrentAccount currentAccount = currentAccountRepository.findById(id).orElseThrow(() ->
                new BankAccountNotFound("Current Account Not Found"));

        currentAccountRepository.delete(currentAccount);
    }

    @Override
    public CurrentResponseDTO getCurrentAccountByAccountNumber(String accountNumber) throws BankAccountNotFound {

        CurrentAccount currentAccount = currentAccountRepository.findCurrentAccountByAccountNumber(accountNumber).orElseThrow(() ->
                new BankAccountNotFound("Current Account Not Found"));
        Optional<ClientResponseDTO> clientByID = accountClient.getClientByID(currentAccount.getClientId());
        return currentAccountMapper.toResponseDTO(currentAccount,clientByID.get());
    }

    @Override
    public CurrentResponseDTO getCurrentAccountByID(Long id) throws BankAccountNotFound {

        CurrentAccount currentAccount = currentAccountRepository.findById(id).orElseThrow(
                () ->
                        new BankAccountNotFound("Current Account Not Found")
        );

        Optional<ClientResponseDTO> clientByID = accountClient.getClientByID(currentAccount.getClientId());

        return currentAccountMapper.toResponseDTO(currentAccount,clientByID.get());
    }

    @Transactional
    @Override
    public CurrentResponseDTO updateCurrentAccountByAccountNumber(String accountNumber, BigDecimal amount) throws BankAccountNotFound {
        CurrentAccount currentAccount = currentAccountRepository.findCurrentAccountByAccountNumber(accountNumber).orElseThrow(() ->
                new BankAccountNotFound("Current Account Not Found"));

        currentAccount.setBalance(amount);

        CurrentAccount save = currentAccountRepository.save(currentAccount);
        Optional<ClientResponseDTO> clientByID = accountClient.getClientByID(save.getClientId());
        return currentAccountMapper.toResponseDTO(save,clientByID.get());
    }

    @Override
    public CurrentResponseDTO getCurrentAccountByClient_ID(Long id) throws BankAccountNotFound {
        Optional<ClientResponseDTO> clientByID = accountClient.getClientByID(id);
        if(clientByID.isPresent()){
            Optional<CurrentAccount> currentAccountByClientId = currentAccountRepository.findCurrentAccountByClientId(clientByID.get().id());
            if(currentAccountByClientId.isPresent()){
                return currentAccountMapper.toResponseDTO(currentAccountByClientId.get(),clientByID.get());
            }
        }
        throw new BankAccountNotFound("The Client Or Bank Account Not Found");
    }


}
