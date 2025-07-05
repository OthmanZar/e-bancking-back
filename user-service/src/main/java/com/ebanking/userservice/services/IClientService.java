package com.ebanking.userservice.services;

import com.ebanking.userservice.dtos.ClientRequestDTO;
import com.ebanking.userservice.dtos.ClientResponseDTO;
import com.ebanking.userservice.exceptions.UserNotFoundException;

import java.util.List;

public interface IClientService {
    ClientResponseDTO createClientAccount (ClientRequestDTO clientRequestDTO);

    ClientRequestDTO updateClientAccount(ClientRequestDTO clientRequestDTO);

    void deleteClient(String reference) throws UserNotFoundException;

    ClientResponseDTO getClientByReference(String reference) throws UserNotFoundException;

    List<ClientResponseDTO> getAllClients();

    ClientResponseDTO getClientByID(Long id) throws UserNotFoundException;


}
