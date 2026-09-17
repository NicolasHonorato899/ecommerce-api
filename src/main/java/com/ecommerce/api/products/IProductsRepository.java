package com.ecommerce.api.payments;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.api.payments.PaymentsModel;

public interface IProductsRepository extends JpaRepository<PaymentsModel, String> {
    PaymentsModel findById(String id);
}
