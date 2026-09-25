package com.ecommerce.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Valid;
import com.ecommerce.api.services.ProductsService;
import com.ecommerce.api.repositories.IUsersRepository;
import com.ecommerce.api.dtos.ProductsRequestDto;
import com.ecommerce.api.dtos.ProductResponseDto;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;
    @Autowired
    private IUsersRepository usersRepository;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductsRequestDto productsDto) {
        var savedProduct = productsService.createProduct(productsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getAllProducts());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String productId){
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getProductById(productId));
    }
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String productId, Authentication authentication, @Valid @RequestBody ProductsRequestDto productsDto) {
        verifyAdminRole(authentication);
        var updatedProduct = productsService.updateProduct(productId, productsDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productId, Authentication authentication){
        verifyAdminRole(authentication);
        boolean deleted = productsService.deleteProduct(productId);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private void verifyAdminRole(Authentication authentication) {
        var user = usersRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        if (!"ADMIN".equals(user.getRole())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to perform this operation");
        }
    }
}
