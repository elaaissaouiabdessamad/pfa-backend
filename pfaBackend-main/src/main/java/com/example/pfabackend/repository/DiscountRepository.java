package com.example.pfabackend.repository;

import com.example.pfabackend.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findByProductCategoryRestaurantId(Long restaurantId);
    Optional<Discount> findByProductId(Long productId);
}
