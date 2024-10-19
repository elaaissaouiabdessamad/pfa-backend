package com.example.pfabackend.dto;

import com.example.pfabackend.entities.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {
    private Long id;
    private String name;
    private Location location;
    private String coverImageUrl;
    private String logoUrl;
    private String cuisine;
    private double rating;
    private String description;
    private String instagram;
    private String phoneNumber;
    private String email;
    private int likes;
    private String priceRange;
}
