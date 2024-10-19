package com.example.pfabackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "products_in_collections")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private ProductCollection collection;

    @Column(name = "quantity")
    private int quantity;
}
