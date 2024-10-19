package com.example.pfabackend.service;

import com.example.pfabackend.entities.Owner;
import com.example.pfabackend.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService  {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    public List<Owner> getOwnersByRestaurantId(Long restaurantId) {
        return ownerRepository.findByRestaurantId(restaurantId);
    }

    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Long id, Owner updatedOwner) {
        if (ownerRepository.existsById(id)) {
            updatedOwner.setId(id);
            return ownerRepository.save(updatedOwner);
        } else {
            throw new RuntimeException("Owner not found with id: " + id);
        }
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
