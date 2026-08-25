package com.ecommerce.api.products;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductsRepository extends JpaRepository<ProductsModel, String> {
    ProductsModel findByProductId(String productId);
}
