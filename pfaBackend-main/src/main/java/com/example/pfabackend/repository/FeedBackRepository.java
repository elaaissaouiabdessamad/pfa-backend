package com.example.pfabackend.repository;

import com.example.pfabackend.entities.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    List<FeedBack> findByRestaurantId(Long restaurantId);

    List<FeedBack> findByClientId(Long clientId);
}
