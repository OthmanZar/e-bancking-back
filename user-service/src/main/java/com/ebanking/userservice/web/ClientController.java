package com.ebanking.userservice.web;

import com.ebanking.userservice.dtos.ClientRequestDTO;
import com.ebanking.userservice.dtos.ClientResponseDTO;
import com.ebanking.userservice.exceptions.UserNotFoundException;
import com.ebanking.userservice.services.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO createdClient = clientService.createClientAccount(clientRequestDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClientRequestDTO> updateClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientRequestDTO updatedClient = clientService.updateClientAccount(clientRequestDTO);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{reference}")
    public ResponseEntity<Void> deleteClient(@PathVariable String reference) throws UserNotFoundException {

            clientService.deleteClient(reference);
            return ResponseEntity.noContent().build();

    }

    @GetMapping("/{reference}")
    public ResponseEntity<ClientResponseDTO> getClient(@PathVariable String reference) {
        try {
            ClientResponseDTO client = clientService.getClientByReference(reference);
            return ResponseEntity.ok(client);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ClientResponseDTO> getClientByID(@PathVariable Long id) {
        try {
            ClientResponseDTO client = clientService.getClientByID(id);
            return ResponseEntity.ok(client);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        List<ClientResponseDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
}
