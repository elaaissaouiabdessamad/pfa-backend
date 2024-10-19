package com.example.pfabackend.service;

import com.example.pfabackend.entities.Discount;
import com.example.pfabackend.entities.Product;
import com.example.pfabackend.repository.DiscountRepository;
import com.example.pfabackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscountService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private  DiscountRepository discountRepository;



    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public List<Discount> getDiscountsByRestaurantId(Long restaurantId) {
        // Implement logic to fetch discounts by restaurant id
        return discountRepository.findByProductCategoryRestaurantId(restaurantId);
    }

    public Discount getDiscountByProductId(Long productId) {
        Optional<Discount> optionalDiscount = discountRepository.findByProductId(productId);
        return optionalDiscount.orElse(null);
    }

    public Discount createOrUpdateDiscount(Long productId, Discount discount) {
        Optional<Discount> existingDiscount = discountRepository.findByProductId(productId);

        if (existingDiscount.isPresent()) {
            Discount currentDiscount = existingDiscount.get();
            currentDiscount.setDiscountPercentage(discount.getDiscountPercentage());
            currentDiscount.setProductCollection(discount.getProductCollection());
            return discountRepository.save(currentDiscount);
        } else {
            if (productRepository.existsById(productId)) {
                Product product = productRepository.findById(productId).get();
                discount.setProduct(product);
                return discountRepository.save(discount);
            } else {
                // Handle case when product does not exist
                return null;
            }
        }
    }


    public Discount updateDiscount(Long discountId, Discount newDiscount) {
        Optional<Discount> optionalDiscount = discountRepository.findById(discountId);
        if (optionalDiscount.isPresent()) {
            Discount existingDiscount = optionalDiscount.get();
            existingDiscount.setProduct(newDiscount.getProduct());
            existingDiscount.setProductCollection(newDiscount.getProductCollection());
            existingDiscount.setDiscountPercentage(newDiscount.getDiscountPercentage());
            return discountRepository.save(existingDiscount);
        } else {
            // Handle discount not found
            return null;
        }
    }

    public void deleteDiscount(Long discountId) {
        discountRepository.deleteById(discountId);
    }
}
