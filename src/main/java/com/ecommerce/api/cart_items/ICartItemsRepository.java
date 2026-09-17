package com.ecommerce.api.cart_items;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.ecommerce.api.carts.CartsModel;
import com.ecommerce.api.products.ProductsModel;

public interface ICartItemsRepository extends JpaRepository<CartItemsModel, String> {
    Optional<CartItemsModel> findByCartAndProduct(CartsModel cart, ProductsModel product);
    List<CartItemsModel> findByCart(CartsModel cart);
    List<CartItemsModel> findByProduct(ProductsModel product);
    //List<CartItemsModel> findByUserEmail(String userEmail);
}
