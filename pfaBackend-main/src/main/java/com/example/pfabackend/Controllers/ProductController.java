package com.example.pfabackend.Controllers;

import com.example.pfabackend.entities.Product;
import com.example.pfabackend.payload.response.MessageResponse;
import com.example.pfabackend.service.FoodCategoryService;
import com.example.pfabackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    private FoodCategoryService foodCategoryService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/activated")
    public ResponseEntity<List<Product>> getAllActivatedProducts() {
        List<Product> activatedProducts = productService.getActivatedProducts();
        return ResponseEntity.ok(activatedProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/foodCategory/{categoryId}")
    public ResponseEntity<?> createProduct(@PathVariable Long categoryId, @RequestPart Product product, @RequestParam("productFile") MultipartFile productFile) {
        Product createdProduct = productService.createProduct(categoryId, product, productFile);
        return ResponseEntity.ok(new MessageResponse("Product of name "+ createdProduct.getName() +" created successfully of the category "+ foodCategoryService.getFoodCategoryById(categoryId).get().getName()));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestPart Product product, @RequestParam("productFile") MultipartFile productFile) {
        Product updatedProduct = productService.updateProduct(productId, product, productFile);
        return ResponseEntity.ok(new MessageResponse("Product of name "+ updatedProduct.getName() +" updated successfully of the category "+productService.getProductById(productId).get().getCategory().getName()));
    }

    @GetMapping(value = "/files/{filename:[a-zA-Z0-9._-]+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = productService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/foodCategory/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/foodCategory/{categoryId}/activated")
    public ResponseEntity<List<Product>> getActivatedProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> activatedProducts = productService.getActivatedProductsByCategoryId(categoryId);
        return ResponseEntity.ok(activatedProducts);
    }

    @PutMapping("/{productId}/deactivate")
    public ResponseEntity<String> deactivateProduct(@PathVariable Long productId) {
        productService.deactivateProduct(productId);
        return ResponseEntity.ok("Product "+ productService.getProductById(productId).get().getName() +" deactivated successfully !");
    }

    @PutMapping("/{productId}/activate")
    public ResponseEntity<String> activateProduct(@PathVariable Long productId) {
        productService.activateProduct(productId);
        return ResponseEntity.ok("Product "+ productService.getProductById(productId).get().getName() +" activated back successfully !");
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
