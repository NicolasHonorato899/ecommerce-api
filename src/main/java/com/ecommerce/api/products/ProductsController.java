package com.ecommerce.api.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private IProductsRepository productRepository;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ProductsModel productsModel) {
        if (productRepository.existsById(productsModel.getProductId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id already exists");
        }
        var savedProduct = productRepository.save(productsModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}
