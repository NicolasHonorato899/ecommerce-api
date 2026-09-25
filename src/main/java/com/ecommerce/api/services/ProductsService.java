package com.ecommerce.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import com.ecommerce.api.repositories.IProductsRepository;
import com.ecommerce.api.models.ProductsModel;
import com.ecommerce.api.dtos.ProductResponseDto;
import com.ecommerce.api.dtos.ProductsRequestDto;


@Service
@Transactional
public class ProductsService {

    @Autowired
    private IProductsRepository productsRepository;

    public List<ProductResponseDto> getAllProducts(){
        return productsRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public ProductResponseDto getProductById(String productId){
        var product = productsRepository.findByProductId(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return toResponseDto(product);
    }

    public boolean existsByProductId(String productId) {
        return productsRepository.existsById(productId);
    }

    public ProductResponseDto createProduct(ProductsRequestDto requestDto){
        if(productsRepository.existsById(requestDto.productId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already exists");
        }

        var product = new ProductsModel();
        product.setProductId(requestDto.productId());
        product.setName(requestDto.name());
        product.setDescription(requestDto.description());
        product.setPrice(requestDto.price());

        var saved = productsRepository.save(product);
        return toResponseDto(saved);
    }

    public ProductResponseDto updateProduct(String productId, ProductsRequestDto requestDto){
        var product = productsRepository.findByProductId(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        product.setName(requestDto.name());
        product.setDescription(requestDto.description());
        product.setPrice(requestDto.price());
        var updated = productsRepository.save(product);
        return toResponseDto(updated);
    }

    public boolean deleteProduct(String productId){
        var product = productsRepository.findByProductId(productId);
        if(product.isPresent()){
            productsRepository.delete(product.get());
            return true;
        }
        return false;
    }

    private ProductResponseDto toResponseDto(ProductsModel product){
        return new ProductResponseDto(
            product.getProductId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
    }
}
