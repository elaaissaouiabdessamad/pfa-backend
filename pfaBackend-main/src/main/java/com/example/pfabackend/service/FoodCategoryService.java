package com.example.pfabackend.service;

import com.example.pfabackend.entities.FoodCategory;
import com.example.pfabackend.entities.Restaurant;
import com.example.pfabackend.repository.FoodCategoryRepository;
import com.example.pfabackend.repository.RestaurantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FoodCategoryService {

    @Autowired
    private  FoodCategoryRepository foodCategoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ProductService productService;

    public List<FoodCategory> getAllFoodCategories() {
        return foodCategoryRepository.findAll();
    }

    public List<FoodCategory> getFoodCategoriesByRestaurantId(Long restaurantId) {
        return foodCategoryRepository.findByRestaurantId(restaurantId);
    }

    public Optional<FoodCategory> getFoodCategoryById(Long id) {
        return foodCategoryRepository.findById(id);
    }

    public FoodCategory saveFoodCategory(Long restaurantId, FoodCategory foodCategory) {
        // Retrieve the restaurant by ID
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if (optionalRestaurant.isPresent()) {

            Optional<FoodCategory> existingCategory = foodCategoryRepository.findByRestaurantIdAndNameIgnoreCase(restaurantId, foodCategory.getName());
            if (existingCategory.isPresent()) {
                throw new IllegalArgumentException("Category with name " + foodCategory.getName() + " already exists for restaurant with ID " + restaurantId);
            }
            foodCategory.setRestaurant(optionalRestaurant.get());
            foodCategory.setIsActivated(true);
            return foodCategoryRepository.save(foodCategory);
        } else {
            // Handle the case where the restaurant ID is not found
            throw new IllegalArgumentException("Restaurant with ID " + restaurantId + " not found");
        }
    }

    @Transactional
    public FoodCategory updateFoodCategory(Long categoryId, FoodCategory updatedCategory) {
        FoodCategory existingCategory = foodCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        existingCategory.setName(updatedCategory.getName());
        return foodCategoryRepository.save(existingCategory);
    }


    @Transactional
    public void deleteFoodCategory(Long categoryId) {
        FoodCategory category = foodCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));

        // Delete associated products individually by passing their IDs
        category.getProducts().forEach(product -> productService.deleteProduct(product.getId()));

        // Finally, delete the category itself
        foodCategoryRepository.delete(category);
    }
}
