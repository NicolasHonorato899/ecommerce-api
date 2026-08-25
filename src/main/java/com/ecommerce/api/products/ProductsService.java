package com.ecommerce.api.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductsService {

    @Autowired
    private IProductsRepository productsRepository;

    public List<ProductsModel> getAllProducts(){
        return productsRepository.findAll();
    }

    public ProductsModel createProduct(ProductsModel productsModel){
        return productsRepository.save(productsModel);
    }

    public ProductsModel updateProduct(ProductsModel productsModel){
        return productsRepository.save(productsModel);
    }

    public boolean deleteProduct(String productId){
        if (productsRepository.existsById(productId)) {
            productsRepository.deleteById(productId);
            return true;
        }
        return false;
    }

    public ProductsModel getProductById(String productId){
        return productsRepository.findById(productId).orElse(null);
    }
}
