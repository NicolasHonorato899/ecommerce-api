package com.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.api.models.ProductsModel;
import java.util.Optional;

public interface IProductsRepository extends JpaRepository<ProductsModel, String> {
    Optional<ProductsModel> findByProductId(String productId);
}
