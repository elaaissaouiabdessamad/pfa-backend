package com.example.pfabackend.service;

import com.example.pfabackend.entities.Client;
import com.example.pfabackend.entities.Restaurant;
import com.example.pfabackend.entities.Waiter;
import com.example.pfabackend.repository.ClientRepository;
import com.example.pfabackend.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            client.setName(updatedClient.getName());
            client.setEmail(updatedClient.getEmail());
            client.setPhone(updatedClient.getPhone());
            return clientRepository.save(client);
        } else {
            return null;
        }
    }



    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public void addRestaurantToFavorites(Long clientId, Long restaurantId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
            if (optionalRestaurant.isPresent()) {
                Restaurant restaurant = optionalRestaurant.get();
                // Check if the restaurant already exists in favorites
                if (!client.getFavoriteRestaurants().contains(restaurant)) {
                    client.getFavoriteRestaurants().add(restaurant);
                    clientRepository.save(client);
                } else {
                    // Restaurant is already in favorites, no action needed
                    System.out.println("Restaurant is already in favorites.");
                }
            } else {
                throw new IllegalArgumentException("Restaurant not found with id: " + restaurantId);
            }
        } else {
            throw new IllegalArgumentException("Client not found with id: " + clientId);
        }
    }
}
