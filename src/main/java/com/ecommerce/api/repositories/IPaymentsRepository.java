package com.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.api.models.PaymentsModel;
import java.util.Optional;

public interface IPaymentsRepository extends JpaRepository<PaymentsModel, String> {
    Optional<PaymentsModel> findByPaymentId(String paymentId);
    Optional<PaymentsModel> findByOrder_OrderId(String orderId);
    Optional<PaymentsModel> findByGatewayTransactionId(String gatewayTransactionId);
}
