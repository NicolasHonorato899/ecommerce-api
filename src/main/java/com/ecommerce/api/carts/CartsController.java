package com.ecommerce.api.carts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/carts")
public class CartsController {
    @Autowired
    private CartsService cartsService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CartsRequestDto cartsDto, Authentication authentication){
        var cartCreated = cartsService.createCart(authentication.getName(), cartsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartCreated);
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCart(@PathVariable String cartId, Authentication authentication){
        return ResponseEntity.status(HttpStatus.OK).body(cartsService.getCartById(cartId, authentication.getName()));
    }
    @PutMapping("/{cartId}")
    public ResponseEntity<?> updateCart(@PathVariable String cartId, @Valid @RequestBody CartsRequestDto cartDto, Authentication authentication){
        var updated = cartsService.updateCart(cartId, cartDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable String cartId, Authentication authentication){
        cartsService.deleteCart(cartId, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body("Cart deleted");
    }
}
