package com.ecommerce.api.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface IOrdersRepository extends JpaRepository<OrdersModel, String>{
    Optional<OrdersModel> findByOrderId(String orderId);
    Optional<OrdersModel> findByUser_Email(String email);
}
