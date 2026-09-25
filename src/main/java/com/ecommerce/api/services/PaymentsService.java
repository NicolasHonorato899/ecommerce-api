package com.ecommerce.api.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import com.ecommerce.api.models.PaymentsModel;
import com.ecommerce.api.models.OrdersModel;
import com.ecommerce.api.repositories.IPaymentsRepository;
import com.ecommerce.api.dtos.PaymentsResponseDto;
import java.util.List;


@Service
@Transactional
public class PaymentsService {

    @Autowired
    private IPaymentsRepository paymentsRepository;


    private PaymentsResponseDto toResponseDto(PaymentsModel payment) {
        return new PaymentsResponseDto(
            payment.getStatus(),
            payment.getPaymentMethod(),
            payment.getPaymentId(),
            payment.getOrder().getOrderId(),
            payment.getAmount(),
            payment.getCurrency(),
            payment.getGateway(),
            payment.getCreatedAt() != null ? java.sql.Timestamp.valueOf(payment.getCreatedAt()) : null,
            payment.getUpdatedAt() != null ? java.sql.Timestamp.valueOf(payment.getUpdatedAt()) : null
        );
    }

    public PaymentsModel createPendingPayment(OrdersModel order, String gatewayTransactionId){
        var payment = new PaymentsModel();
        payment.setPaymentId(java.util.UUID.randomUUID().toString());
        payment.setOrder(order);
        payment.setAmount(order.getAmount().floatValue());
        payment.setCurrency("BRL");
        payment.setStatus("pending");
        payment.setGateway("Stripe");
        payment.setGatewayTransactionId(gatewayTransactionId);
        return paymentsRepository.save(payment);
    }

    public PaymentsResponseDto findByOrderId(String orderId, String requesterEmail){
        var payment = paymentsRepository.findByOrder_OrderId(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found for orderId: " + orderId));

        if(!payment.getOrder().getUser().getEmail().equals(requesterEmail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this payment");
        }
        return toResponseDto(payment);
    }

    public List<PaymentsResponseDto> findAll(String requesterEmail, boolean isAdmin){
        var payments = paymentsRepository.findAll();
        return payments.stream()
            .filter(payment -> isAdmin || payment.getOrder().getUser().getEmail().equals(requesterEmail))
            .map(this::toResponseDto)
            .toList();
    }

    public void markAsPaid(String gatewayTransactionId){
        var payment = paymentsRepository.findByGatewayTransactionId(gatewayTransactionId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        payment.setStatus("succeeded");
        paymentsRepository.save(payment);
    }
}
