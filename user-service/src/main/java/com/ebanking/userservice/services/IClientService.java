package com.ebanking.userservice.services;

import com.ebanking.userservice.dtos.ClientRequestDTO;
import com.ebanking.userservice.exceptions.UserNotFoundException;

import java.util.List;

public interface IClientService {
    ClientRequestDTO createClientAccount (ClientRequestDTO clientRequestDTO);

    ClientRequestDTO updateClientAccount(ClientRequestDTO clientRequestDTO);

    void deleteClient(String reference) throws UserNotFoundException;

    ClientRequestDTO getClientByReference(String reference) throws UserNotFoundException;

    List<ClientRequestDTO> getAllClients();

    ClientRequestDTO getClientByID(Long id) throws UserNotFoundException;


}
