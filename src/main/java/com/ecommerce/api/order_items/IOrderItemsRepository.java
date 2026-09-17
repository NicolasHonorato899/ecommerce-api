package com.ecommerce.api.order_items;

import org.springframework.data.jpa.repository.JpaRepository;
public interface IOrderItemsRepository extends JpaRepository<OrderItemsModel, String> {
    OrderItemsModel findByOrderOrderIdAndProductProductId(String orderId, String productId);
}
