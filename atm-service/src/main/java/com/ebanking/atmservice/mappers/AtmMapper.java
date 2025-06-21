package com.ebanking.atmservice.mappers;

import com.ebanking.atmservice.dtos.ATMResponseDTO;
import com.ebanking.atmservice.entities.ATM;
import org.springframework.stereotype.Service;

@Service
public class AtmMapper {

    public ATMResponseDTO toATMResponseDTO(ATM atm) {
        return  new ATMResponseDTO(
                atm.getReference(),
                atm.getAtmCode(),
                atm.getLocation(),
                atm.getStatus(),
                atm.getMaxWithdrawLimit(),
                atm.getCity()
        );
    }
}
