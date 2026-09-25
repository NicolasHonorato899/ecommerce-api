package com.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;
import com.ecommerce.api.models.OrdersModel;

public interface IOrdersRepository extends JpaRepository<OrdersModel, String>{
    Optional<OrdersModel> findByOrderId(String orderId);
    Optional<OrdersModel> findByUser_Email(String email);
}
