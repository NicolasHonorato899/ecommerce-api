package com.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ecommerce.api.models.UsersModel;
import com.ecommerce.api.models.CartsModel;


public interface ICartsRepository extends JpaRepository<CartsModel, String> {
    CartsModel findByCartId(String cartId);
    Optional<CartsModel> findByUserAndStatus(UsersModel user, String status);
}
