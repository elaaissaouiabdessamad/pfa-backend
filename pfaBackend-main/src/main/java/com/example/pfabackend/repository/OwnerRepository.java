package com.example.pfabackend.repository;

import com.example.pfabackend.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Long> {
    List<Owner> findByRestaurantId(Long restaurantId);
}
