package com.example.pfabackend.repository;

import com.example.pfabackend.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RewardRepository extends JpaRepository<Reward, Long> {
    @Query("SELECT r FROM Reward r " +
            "JOIN r.product p " +
            "JOIN p.category c " +
            "JOIN c.restaurant rest " +
            "WHERE rest.id = :restaurantId")
    List<Reward> findAllByRestaurantId(Long restaurantId);

    Optional<Reward> findByProductId(Long productId);
}
