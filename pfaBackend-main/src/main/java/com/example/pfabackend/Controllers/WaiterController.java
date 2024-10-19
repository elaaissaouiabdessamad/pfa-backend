package com.example.pfabackend.Controllers;

import com.example.pfabackend.entities.Waiter;
import com.example.pfabackend.service.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/waiters")
public class WaiterController {

    @Autowired
    private WaiterService waiterService;

    @GetMapping
    public List<Waiter> getAllWaiters() {
        return waiterService.getAllWaiters();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Waiter> getWaiterById(@PathVariable Long id) {
        Waiter waiter = waiterService.getWaiterById(id);
        if (waiter != null) {
            return ResponseEntity.ok().body(waiter);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/restaurant/{restaurantId}")
    public ResponseEntity<Waiter> createWaiterForRestaurant(@PathVariable Long restaurantId, @RequestBody Waiter newWaiter) {
        Waiter createdWaiter = waiterService.createWaiterForRestaurant(restaurantId, newWaiter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWaiter);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Waiter>> getWaitersByRestaurantId(@PathVariable Long restaurantId) {
        List<Waiter> waiters = waiterService.getWaitersByRestaurantId(restaurantId);
        return ResponseEntity.ok().body(waiters);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Waiter> updateWaiter(@PathVariable Long id, @RequestBody Waiter updatedWaiter) {
        Waiter updated = waiterService.updateWaiter(id, updatedWaiter);
        if (updated != null) {
            return ResponseEntity.ok().body(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWaiter(@PathVariable Long id) {
        waiterService.deleteWaiter(id);
        return ResponseEntity.noContent().build();
    }
}
