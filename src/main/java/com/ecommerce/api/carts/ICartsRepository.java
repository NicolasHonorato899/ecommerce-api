package com.ecommerce.api.carts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartsRepository extends JpaRepository<CartsModel, String> {
}
