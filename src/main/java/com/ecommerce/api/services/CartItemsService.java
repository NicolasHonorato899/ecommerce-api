package com.ecommerce.api.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.ecommerce.api.models.CartsModel;
import com.ecommerce.api.repositories.ICartsRepository;
import com.ecommerce.api.repositories.IProductsRepository;
import com.ecommerce.api.repositories.ICartItemsRepository;
import com.ecommerce.api.models.CartItemsModel;
import com.ecommerce.api.models.ProductsModel;
import com.ecommerce.api.models.UsersModel;
import com.ecommerce.api.dtos.CartItemsResponseDto;
import com.ecommerce.api.dtos.CartItemsRequestDto;

@Service
@Transactional
public class CartItemsService {

    @Autowired
    private ICartItemsRepository cartItemsRepository;
    @Autowired
    private ICartsRepository cartsRepository;
    @Autowired
    private IProductsRepository productsRepository;

    public CartItemsResponseDto addItem(String cartId, CartItemsRequestDto requestDto, String requesterEmail){
        var cart = getOwnedCart(cartId, requesterEmail);
        var product = productsRepository.findById(requestDto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if(product.getStock() < requestDto.quantity()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
        }

        var existingCartItem = cartItemsRepository.findByCartAndProduct(cart, product);

        CartItemsModel item;

        if(existingCartItem.isPresent()){
            item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + requestDto.quantity());
        }else{
            item = new CartItemsModel();
            item.setId(UUID.randomUUID().toString());
            item.setProduct(product);
            item.setCart(cart);
            item.setQuantity(requestDto.quantity());
        }

        var saved = cartItemsRepository.save(item);
        return toResponseDto(saved);
    }

    public List<CartItemsResponseDto> getCartItems(String cartId, String requesterEmail){
        var cart = getOwnedCart(cartId, requesterEmail);
        return cartItemsRepository.findByCart(cart)
            .stream()
            .map(this::toResponseDto)
            .toList();
    }

    public CartItemsResponseDto updateItem(String cartItemId, CartItemsRequestDto requestDto, String requesterEmail){
        var cartItem = getCartItem(cartItemId, requesterEmail);
        var product = productsRepository.findById(requestDto.productId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        if(product.getStock() < requestDto.quantity()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock");
        }

        cartItem.setProduct(product);
        cartItem.setQuantity(requestDto.quantity());
        var saved = cartItemsRepository.save(cartItem);
        return toResponseDto(saved);
    }

    public void deleteItem(String cartItemId, String requesterEmail){
        var cartItem = getCartItem(cartItemId, requesterEmail);
        cartItemsRepository.delete(cartItem);
    }

    private CartsModel getOwnedCart(String cartId, String requesterEmail){
        var cart = cartsRepository.findByCartId(cartId);
        if(cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
        checkOwnership(cart, requesterEmail);
        return cart;
    }

    private CartItemsModel getCartItem(String cartItemId, String requesterEmail){
        var cartItem = cartItemsRepository.findById(cartItemId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart item not found"));
        checkOwnership(cartItem.getCart(), requesterEmail);
        return cartItem;
    }

    private void checkOwnership(CartsModel cart, String requesterEmail){
        if(!cart.getUser().getEmail().equals(requesterEmail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this cart");
        }
    }

    private CartItemsResponseDto toResponseDto(CartItemsModel cartItem){
        double unitPrice = cartItem.getProduct().getPrice();
        double subtotal = unitPrice * cartItem.getQuantity();
        return new CartItemsResponseDto(
            cartItem.getId(),
            cartItem.getProduct().getProductId(),
            cartItem.getProduct().getName(),
            cartItem.getQuantity(),
            unitPrice,
            subtotal
        );
    }

}
