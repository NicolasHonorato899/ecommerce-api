package com.ecommerce.api.cart_items;

import
org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/cart_items")
public class CartItemsController {
    @Autowired
    private CartItemsService cartItemsService;

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartItems(@PathVariable String cartId, Authentication authentication) {
        var cartItems = cartItemsService.getCartItems(cartId, authentication.getName());
        return ResponseEntity.ok(cartItems);
    }
    @PostMapping("/{cartId}")
    public ResponseEntity<?> addCartItem(@PathVariable String cartId, @Valid @RequestBody CartItemsRequestDto cartItemsRequest, Authentication authentication){
        var cartItem = cartItemsService.addItem(cartId, cartItemsRequest, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
    }
    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable String cartItemId, @Valid @RequestBody CartItemsRequestDto cartItems, Authentication authentication){
        var updatedCartItem = cartItemsService.updateItem(cartItemId, cartItems, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(updatedCartItem);
    }
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable String cartItemId, Authentication authentication){
        cartItemsService.deleteItem(cartItemId, authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
