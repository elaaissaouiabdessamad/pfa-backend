package com.example.pfabackend.service;

import com.example.pfabackend.entities.Restaurant;
import com.example.pfabackend.entities.Waiter;
import com.example.pfabackend.repository.RestaurantRepository;
import com.example.pfabackend.repository.WaiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WaiterService {

    @Autowired
    private WaiterRepository waiterRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Waiter> getAllWaiters() {
        return waiterRepository.findAll();
    }

    public Waiter getWaiterById(Long id) {
        Optional<Waiter> optionalWaiter = waiterRepository.findById(id);
        return optionalWaiter.orElse(null);
    }

    public Waiter createWaiterForRestaurant(Long restaurantId, Waiter newWaiter) {
        // Retrieve the restaurant by its ID
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();

            // Set the restaurant for the new waiter
            newWaiter.setRestaurant(restaurant);

            // Save the new waiter
            return waiterRepository.save(newWaiter);
        } else {
            throw new IllegalArgumentException("Restaurant not found with ID: " + restaurantId);
        }
    }

    public List<Waiter> getWaitersByRestaurantId(Long restaurantId) {
        // Retrieve the restaurant by its ID
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);

        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            return restaurant.getWaiters();
        } else {
            throw new IllegalArgumentException("Restaurant not found with ID: " + restaurantId);
        }
    }

    public Waiter updateWaiter(Long id, Waiter updatedWaiter) {
        Optional<Waiter> optionalWaiter = waiterRepository.findById(id);
        if (optionalWaiter.isPresent()) {
            Waiter waiter = optionalWaiter.get();
            waiter.setName(updatedWaiter.getName());
            waiter.setCin(updatedWaiter.getCin());
            waiter.setPhone(updatedWaiter.getPhone());
            waiter.setEmail(updatedWaiter.getEmail());
            // Update other fields as needed
            return waiterRepository.save(waiter);
        } else {
            return null; // Waiter with given id not found
        }
    }

    public void deleteWaiter(Long id) {
        waiterRepository.deleteById(id);
    }
}
