package com.blagovestkabov.paymentservice.service;

import com.blagovestkabov.paymentservice.model.PaymentRequest;
import com.blagovestkabov.paymentservice.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);

}
