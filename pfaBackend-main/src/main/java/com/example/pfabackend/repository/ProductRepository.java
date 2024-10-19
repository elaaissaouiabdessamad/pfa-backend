package com.example.pfabackend.repository;

import com.example.pfabackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory_Id(Long categoryId);
    List<Product> findByCategory_IdAndIsActivated(Long categoryId, boolean isActivated);

    @Query("SELECT p FROM Product p WHERE p.isActivated = true")
    List<Product> findActivatedProducts();
}
