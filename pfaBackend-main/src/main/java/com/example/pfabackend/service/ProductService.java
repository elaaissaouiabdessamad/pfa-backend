package com.example.pfabackend.service;

import com.example.pfabackend.entities.FoodCategory;
import com.example.pfabackend.entities.Product;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.repository.FoodCategoryRepository;
import com.example.pfabackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    private final Path root = Paths.get("uploads/product");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Autowired
    public ProductService(ProductRepository productRepository, FoodCategoryRepository foodCategoryRepository) {
        this.productRepository = productRepository;
        this.foodCategoryRepository = foodCategoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getActivatedProducts() {
        return productRepository.findActivatedProducts();
    }

    public List<Product> getActivatedProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategory_IdAndIsActivated(categoryId, true);
    }
    public void deactivateProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setIsActivated(false); // Set isActivated to false
            productRepository.save(product); // Save the updated product
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    public void activateProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setIsActivated(true); // Set isActivated to false
            productRepository.save(product); // Save the updated product
        } else {
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Long categoryId, Product product, MultipartFile productFile) {
        Optional<FoodCategory> categoryOptional = foodCategoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()) {
            if (productFile == null || productFile.isEmpty()) {
                throw new IllegalArgumentException("Product file cannot be null or empty.");
            }
            init();
            String randomProductFileName = UUID.randomUUID().toString();
            String fileProductExtension = StringUtils.getFilenameExtension(productFile.getOriginalFilename());
            String combinedProductFileName = randomProductFileName + "." + fileProductExtension;
            saveProductImage(productFile, combinedProductFileName);
            System.out.println("Product Created !");
            System.out.println("Name: "+product.getName()+" ,info: "+product.getInfo()+" , id: "+ product.getId());
            product.setImg(combinedProductFileName);
            FoodCategory category = categoryOptional.get();
            product.setCategory(category);
            product.setIsActivated(true);
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Food category with ID " + categoryId + " not found.");
        }
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }

    public Product updateProduct(Long id, Product updatedProduct, MultipartFile productFile) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            // Update fields if provided
            if (updatedProduct.getName() != null) {
                product.setName(updatedProduct.getName());
            }
            if (updatedProduct.getPrice() != 0) {
                product.setPrice(updatedProduct.getPrice());
            }
            if (updatedProduct.getBonusPoints() != 0) {
                product.setBonusPoints(updatedProduct.getBonusPoints());
            }
            if (updatedProduct.getInfo() != null) {
                product.setInfo(updatedProduct.getInfo());
            }
            if (productFile == null || productFile.isEmpty()) {
                throw new IllegalArgumentException("Product file cannot be null or empty.");
            }
            init();
            String randomProductFileName = UUID.randomUUID().toString();
            String fileProductExtension = StringUtils.getFilenameExtension(productFile.getOriginalFilename());
            String combinedProductFileName = randomProductFileName + "." + fileProductExtension;
            saveProductImage(productFile, combinedProductFileName);
            System.out.println("Product Created !");
            System.out.println("Name: " + product.getName() + " ,info: " + product.getInfo() + " , id: " + product.getId());
            product.setImg(combinedProductFileName);
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
    }

    public void saveProductImage(MultipartFile file, String filename) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(filename));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            String nameProductDeleted = product.getName();
            productRepository.delete(product);
            return ResponseEntity.ok(new MessageResponse("Product of name "+ nameProductDeleted +" deleted successfully !"));
        } else {
            return ResponseEntity.ok(new MessageResponse("Product with ID " + id + " not found."));
        }
    }

}
