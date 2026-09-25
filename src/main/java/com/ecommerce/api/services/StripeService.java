package com.ecommerce.api.services;

import org.springframework.stereotype.Service;
import com.ecommerce.api.models.OrdersModel;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import com.stripe.Stripe;
import java.util.List;
import com.ecommerce.api.models.OrderItemsModel;
import com.ecommerce.api.dtos.StripeCheckoutResultDto;

@Service
public class StripeService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    public StripeCheckoutResultDto createCheckoutSession(OrdersModel order, List<OrderItemsModel> orderItems) {
        Stripe.apiKey = secretKey;

        var sessionParamsBuilder = SessionCreateParams.builder()
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:8080/orders/" + order.getId() + "/success")
            .setCancelUrl("http://localhost:8080/orders/" + order.getId() + "/cancel");

        for(OrderItemsModel item : orderItems){
            var productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();

            long unitAmount = Math.round(item.getUnitPrice()*100);

            var priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("BRL")
                .setUnitAmount(unitAmount)
                .setProductData(productData)
                .build();

            var lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity((long) item.getQuantity())
                .setPriceData(priceData)
                .build();

            sessionParamsBuilder.addLineItem(lineItem);
        }

        try {
            Session session = Session.create(sessionParamsBuilder.build());
            return new StripeCheckoutResultDto(session.getId(), session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
