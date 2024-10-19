package com.example.pfabackend.repository;

import com.example.pfabackend.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {


    List<Restaurant> findByNameContains(String mc);


    //or use  but add %c% instead of c as parameter
    @Query("select r from Restaurant r where r.name Like :x")
    List<Restaurant> Search(@Param("x") String mc );
}
