package com.example.pfabackend.dto;

import com.example.pfabackend.entities.Discount;
import com.example.pfabackend.entities.Product;
import com.example.pfabackend.entities.ProductCollection;
import com.example.pfabackend.entities.Reward;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class OrderRequestDto {
    private Set<Product> products;
    private Set<Discount> discounts;
    private Set<Reward> rewards;
    private double totalPrice;
}
