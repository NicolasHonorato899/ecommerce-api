package com.ecommerce.api.payments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;


@Service
@Transactional
public class PaymentsService {

    @Autowired
    private IPaymentsRepository paymentsRepository;


    private PaymentsResponseDto toResponseDto(PaymentsModel payment) {
        return new PaymentsResponseDto(
            payment.getPaymentId(),
            payment.getOrderId(),
            payment.getAmount(),
            payment.getCurrency(),
            payment.getStatus(),
            payment.getPaymentMethod(),
            payment.getGateway(),
            payment.getCreatedAt(),
            payment.getUpdatedAt()
        );
    }

    public PaymentsModel createPendingPayment(OrdersModel order){
        var payment = new PaymentsModel();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setOrder(order);
        payment.setAmount(order.getAmount());
        payment.setCurrency("BRL");
        payment.setStatus("pending");
        payment.setGateway("Stripe");
        return paymentsRepository.save(payment);
    }

    public PaymentsResponseDto findByOrderId(String orderId, String requesterEmail){
        var payment = paymentsRepository.findByOrder_OrderId(orderId);
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found for orderId: " + orderId));

        if(!payment.getOrder().getUser().getEmail().equals(requesterEmail)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this payment");
        }
        return toResponseDto(payment);
    }

    void markAsPaid(String gatewayTransactionId){
        var payment = paymentsRepository.findByGatewayTransactionId(gatewayTransactionId);
        payment.setStatus("succeded");
        paymentsRepository.save(payment);
    }
}
