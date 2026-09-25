package com.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.api.models.OrderItemsModel;

public interface IOrderItemsRepository extends JpaRepository<OrderItemsModel, String> {
    OrderItemsModel findByOrderOrderIdAndProductProductId(String orderId, String productId);
}
