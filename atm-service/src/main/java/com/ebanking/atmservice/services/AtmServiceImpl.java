package com.ebanking.atmservice.services;

import com.ebanking.atmservice.clients.TransactionClient;
import com.ebanking.atmservice.dtos.ATMRequestDTO;
import com.ebanking.atmservice.dtos.ATMResponseDTO;
import com.ebanking.atmservice.dtos.WithdrawalRequestDTO;
import com.ebanking.atmservice.dtos.WithdrawalResponseDTO;
import com.ebanking.atmservice.entities.ATM;
import com.ebanking.atmservice.enums.ATMStatus;
import com.ebanking.atmservice.exceptions.AtmNotFoundException;
import com.ebanking.atmservice.mappers.AtmMapper;
import com.ebanking.atmservice.repositories.ATMRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtmServiceImpl implements IAtmService {

    private final ATMRepository atmRepository;
    private final AtmMapper atmMapper;
    private final TransactionClient transactionClient;
    @Override
    public ATMResponseDTO createAtm(ATMRequestDTO atmRequestDTO) {

        int indexLast = atmRepository.countATMSByCity(atmRequestDTO.city());
        String atmCode = generateATMCode(atmRequestDTO.city(),indexLast);

        ATM atm = new ATM();
        atm.setReference(UUID.randomUUID().toString());
        atm.setStatus(ATMStatus.AVAILABLE);
        atm.setLocation(atmRequestDTO.Location());
        atm.setMaxWithdrawLimit(atmRequestDTO.maxWithdrawal());
        atm.setCity(atmRequestDTO.city());
        atm.setAtmCode(atmCode);

        ATM save = atmRepository.save(atm);
        return atmMapper.toATMResponseDTO(save);
    }

    private String generateATMCode(String city, int lastIndex) {
        String upperCity = city.trim().toUpperCase();
        String number = String.format("%03d", lastIndex + 1); // pad with leading zeros
        return "ATM-" + upperCity + "-" + number;
    }

    @Override
    public ATMResponseDTO getAtm(String reference) throws AtmNotFoundException {

        return findATMByReference(reference);
    }

    private ATMResponseDTO findATMByReference(String reference) throws AtmNotFoundException {
        ATM atm = atmRepository.findByAtmCode(reference).orElseThrow(() ->
                new AtmNotFoundException("ATM Not Found")
        );
        return atmMapper.toATMResponseDTO(atm);
    }

    @Transactional
    @Override
    public WithdrawalResponseDTO withdrawATM(WithdrawalRequestDTO withdrawalRequestDTO) throws AtmNotFoundException, JsonProcessingException {

        ATMResponseDTO atmResponseDTO = findATMByReference(withdrawalRequestDTO.atmCode());

        int compared = atmResponseDTO.maxWithdrawLimit().compareTo(withdrawalRequestDTO.amount());

        if (compared >=0) {
            WithdrawalRequestDTO withdrawalRequestDTO1 = new WithdrawalRequestDTO(
                    withdrawalRequestDTO.cardNumber(),
              withdrawalRequestDTO.amount(),
              atmResponseDTO.atmCode()

            );

            WithdrawalResponseDTO withdrawalResponseDTO = transactionClient.withdraw(withdrawalRequestDTO1).orElseThrow(
                    () -> new AtmNotFoundException("Something went wrong during transaction.") );



            return new WithdrawalResponseDTO(
                    withdrawalResponseDTO.cardID(),
                    withdrawalRequestDTO.amount(),
                    withdrawalResponseDTO.amount(),
                    withdrawalResponseDTO.transactionDate(),
                    withdrawalResponseDTO.status(),
                    withdrawalRequestDTO.atmCode()
            );
        }else{
            throw new AtmNotFoundException("ATM Max Withdraw Limit Exceeded");
        }


    }
}
