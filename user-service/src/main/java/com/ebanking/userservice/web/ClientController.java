package com.ebanking.userservice.web;

import com.ebanking.userservice.dtos.ClientRequestDTO;
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
    public ResponseEntity<ClientRequestDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        ClientRequestDTO createdClient = clientService.createClientAccount(clientRequestDTO);
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
    public ResponseEntity<ClientRequestDTO> getClient(@PathVariable String reference) {
        try {
            ClientRequestDTO client = clientService.getClientByReference(reference);
            return ResponseEntity.ok(client);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientRequestDTO>> getAllClients() {
        List<ClientRequestDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
}
