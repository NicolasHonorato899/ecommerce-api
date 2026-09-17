package com.ecommerce.api.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody ProductsRequestDto productsDto) {
        var savedProduct = productsService.createProduct(productsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProducts());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getProductById(productId));
    }
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, @Valid @RequestBody ProductsRequestDto productsDto) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable String productId){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }
}
