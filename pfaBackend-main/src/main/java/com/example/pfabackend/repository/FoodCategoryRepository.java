package com.example.pfabackend.repository;

import com.example.pfabackend.entities.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {
    List<FoodCategory> findByRestaurantId(Long restaurantId);

    Optional<FoodCategory> findByRestaurantIdAndNameIgnoreCase(Long restaurantId, String name);
}
