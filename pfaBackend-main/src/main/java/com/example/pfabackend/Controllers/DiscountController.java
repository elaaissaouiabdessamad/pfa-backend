package com.example.pfabackend.Controllers;

import com.example.pfabackend.entities.Discount;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {


    @Autowired
    private  DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts() {
        List<Discount> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Discount>> getDiscountsByRestaurantId(@PathVariable Long restaurantId) {
        List<Discount> discounts = discountService.getDiscountsByRestaurantId(restaurantId);
        return ResponseEntity.ok(discounts);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Discount> getDiscountByProductId(@PathVariable Long productId) {
        Discount discount = discountService.getDiscountByProductId(productId);
        return ResponseEntity.ok(discount);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<?> createOrUpdateDiscount(@PathVariable Long productId, @RequestBody Discount discount) {
        Discount createdOrUpdatedDiscount = discountService.createOrUpdateDiscount(productId, discount);
        if (createdOrUpdatedDiscount != null) {
            return ResponseEntity.ok(new MessageResponse("Discount of percentage: "+ createdOrUpdatedDiscount.getDiscountPercentage() +" %, created successfully of the product "+ createdOrUpdatedDiscount.getProduct().getName()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Discount is empty !!!"));
        }
    }

    @PutMapping("/product/{discountId}")
    public ResponseEntity<?> updateDiscount(@PathVariable Long discountId, @RequestBody Discount discount) {
        Discount updatedDiscount = discountService.updateDiscount(discountId, discount);
        if (updatedDiscount != null) {
            return ResponseEntity.ok(new MessageResponse("Discount of percentage: "+ updatedDiscount.getDiscountPercentage() +" %, updated successfully of the product "+ updatedDiscount.getProduct().getName()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Discount is empty !!!"));
        }
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long discountId) {
        discountService.deleteDiscount(discountId);
        return ResponseEntity.noContent().build();
    }
}
