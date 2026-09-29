package com.ecommerce.api.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;
import com.ecommerce.api.repositories.ICartsRepository;
import com.ecommerce.api.repositories.IUsersRepository;
import org.springframework.http.HttpStatus;
import com.ecommerce.api.models.CartsModel;
import com.ecommerce.api.dtos.CartsResponseDto;
import com.ecommerce.api.dtos.CartsRequestDto;
import java.time.LocalDateTime;

@Service
@Transactional
public class CartsService {
    @Autowired
    private ICartsRepository cartsRepository;

    @Autowired
    private IUsersRepository usersRepository;

    public List<CartsResponseDto> getAllCarts() {
        return cartsRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public CartsResponseDto getCartById(String cartId, String requesterEmail) {
        var cart = cartsRepository.findByCartId(cartId);
        if(cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }
        if(!cart.getUser().getEmail().equals(requesterEmail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this cart");
        }
        return toResponseDto(cart);
    }

    public boolean existsById(String cartId){
        return cartsRepository.existsById(cartId);
    }

    public CartsResponseDto createCart(String requesterEmail, CartsRequestDto cartDto) {
        var user = usersRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        var existingActiveCart = cartsRepository.findByUserAndStatus(user, "active");
        if(existingActiveCart.isPresent()){
            return toResponseDto(existingActiveCart.get());
        }
        var cart = new CartsModel();
        cart.setCartId(UUID.randomUUID().toString());
        cart.setStatus(cartDto.status() != null ? cartDto.status() : "active");
        cart.setUser(user);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        var saved = cartsRepository.save(cart);
        return toResponseDto(saved);
    }

    public CartsResponseDto updateCart(String cartId, CartsRequestDto cartDto, String requesterEmail){
        var user = usersRepository.findByEmail(requesterEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        var cart = cartsRepository.findByCartId(cartId);
        if(cart == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart not found");
        }
        cart.setStatus(cartDto.status() != null ? cartDto.status(): "active");
        cart.setUser(user);
        var updated = cartsRepository.save(cart);
        return toResponseDto(updated);
    }

    public boolean deleteCart(String cartId, String requesterEmail){
        var cart = cartsRepository.findByCartId(cartId);
        if(cart == null){
            return false;
        }
        cartsRepository.delete(cart);
        return true;
    }

    private CartsResponseDto toResponseDto(CartsModel cart){
        return new CartsResponseDto(
                cart.getCartId(),
                cart.getUser().getEmail(),
                cart.getStatus(),
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }
}
