package com.example.pfabackend.Controllers;

import com.example.pfabackend.entities.Restaurant;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/imagesIncluded")
    public ResponseEntity<List<Restaurant>> getAllRestaurantsWithLinks() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        String baseUrl = "http://localhost:8080/api/restaurants/files/";

        restaurants.forEach(restaurant -> {
            if (restaurant.getCoverImageUrl() != null) {
                restaurant.setCoverImageUrl(baseUrl + restaurant.getCoverImageUrl());
            }
            if (restaurant.getLogoUrl() != null) {
                restaurant.setLogoUrl(baseUrl + restaurant.getLogoUrl());
            }
        });

        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/owner/{id}")
    public Restaurant getRestaurantByOwnerId(@PathVariable Long id){
        return restaurantService.getRestaurantByOwnerId(id);
    }



    @PostMapping
    public ResponseEntity<?> createRestaurant(@RequestPart Restaurant restaurant, @RequestParam("logoFile") MultipartFile logoFile, @RequestParam("coverFile") MultipartFile coverFile, @RequestParam("ownerId") String ownerId ) {
        if (logoFile == null || logoFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Logo file is empty !"));
        }
        if (coverFile == null || coverFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Cover file is empty !"));
        }
        restaurantService.createRestaurant(restaurant, logoFile, coverFile, ownerId);
        return ResponseEntity.ok(new MessageResponse("Restaurant created successfully! Check Menu or Rewards!"));
    }


    @PutMapping
    public ResponseEntity<?> updateRestaurant(@RequestPart Restaurant restaurant, @RequestParam("logoFile") MultipartFile logoFile, @RequestParam("coverFile") MultipartFile coverFile, @RequestParam("restaurantId") String restaurantId ) {
        restaurantService.updateRestaurant(Long.parseLong(restaurantId), logoFile, coverFile, restaurant);
        return ResponseEntity.ok(new MessageResponse("Restaurant updated successfully!"));
    }

    //get any image of restaurant (cover or logo) by sending get: http://localhost:8084/api/restaurants/files/<<file_name.ext>>
    @GetMapping(value = "/files/{filename:[a-zA-Z0-9._-]+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = restaurantService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }


}
