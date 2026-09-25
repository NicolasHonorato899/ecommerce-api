package com.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.ecommerce.api.models.CartsModel;
import com.ecommerce.api.models.CartItemsModel;
import com.ecommerce.api.models.ProductsModel;

public interface ICartItemsRepository extends JpaRepository<CartItemsModel, String> {
    Optional<CartItemsModel> findByCartAndProduct(CartsModel cart, ProductsModel product);
    List<CartItemsModel> findByCart(CartsModel cart);
    List<CartItemsModel> findByProduct(ProductsModel product);
    //List<CartItemsModel> findByUserEmail(String userEmail);
}
