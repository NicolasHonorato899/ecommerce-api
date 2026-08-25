package com.ecommerce.api.payments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentsRepository extends JpaRepository<PaymentsModel, String> {
    PaymentsModel findByPaymentId(String paymentId);
}
