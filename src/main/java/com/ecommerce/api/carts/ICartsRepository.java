package com.ecommerce.api.carts;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ecommerce.api.users.UsersModel;


public interface ICartsRepository extends JpaRepository<CartsModel, String> {
    CartsModel findByCartId(String cartId);
    Optional<CartsModel> findByUserAndStatus(UsersModel user, String status);
}
