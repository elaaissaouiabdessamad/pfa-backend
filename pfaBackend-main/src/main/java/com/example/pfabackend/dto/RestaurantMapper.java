package com.example.pfabackend.dto;
import com.example.pfabackend.entities.Restaurant;

public class RestaurantMapper {
    public static RestaurantDTO toDTO(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setLocation(restaurant.getLocation());
        dto.setCoverImageUrl(restaurant.getCoverImageUrl());
        dto.setLogoUrl(restaurant.getLogoUrl());
        dto.setCuisine(restaurant.getCuisine());
        dto.setRating(restaurant.getRating());
        dto.setDescription(restaurant.getDescription());
        dto.setInstagram(restaurant.getInstagram());
        dto.setPhoneNumber(restaurant.getPhoneNumber());
        dto.setEmail(restaurant.getEmail());
        dto.setLikes(restaurant.getLikes());
        dto.setPriceRange(restaurant.getPriceRange());
        return dto;
    }
}
