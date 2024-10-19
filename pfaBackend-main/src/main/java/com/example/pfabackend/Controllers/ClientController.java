package com.example.pfabackend.Controllers;

import com.example.pfabackend.entities.Client;
import com.example.pfabackend.entities.Waiter;
import com.example.pfabackend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }


    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        return clientService.updateClient(id, updatedClient);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @PutMapping("/{clientId}/add-favorite-restaurant/{restaurantId}")
    public ResponseEntity<String> addFavoriteRestaurant(@PathVariable Long clientId, @PathVariable Long restaurantId) {
        try {
            clientService.addRestaurantToFavorites(clientId, restaurantId);
            return ResponseEntity.ok("Restaurant added to favorites successfully for client ID: " + clientId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
