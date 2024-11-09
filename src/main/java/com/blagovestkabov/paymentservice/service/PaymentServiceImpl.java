package com.blagovestkabov.paymentservice.service;

import com.blagovestkabov.paymentservice.entity.TransactionDetails;
import com.blagovestkabov.paymentservice.model.PaymentMethod;
import com.blagovestkabov.paymentservice.model.PaymentRequest;
import com.blagovestkabov.paymentservice.model.PaymentResponse;
import com.blagovestkabov.paymentservice.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Payment details: {}", paymentRequest);

        TransactionDetails transactionDetails =
                TransactionDetails
                        .builder()
                        .paymentStatus("SUCCESS")
                        .paymentMethod(paymentRequest.getPaymentMethod().name())
                        .paymentDate(Instant.now())
                        .refNumber(paymentRequest.getReferenceNumber())
                        .orderId(paymentRequest.getOrderId())
                        .amount(paymentRequest.getAmount())
                        .build();

        transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction {} completed. ", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        TransactionDetails transactionDetails
                = transactionDetailsRepository.findByOrderId(orderId);
        return PaymentResponse
                .builder()
                .paymentId(transactionDetails.getId())
                .amount(transactionDetails.getAmount())
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentMethod(PaymentMethod.valueOf(transactionDetails.getPaymentMethod()))
                .status(transactionDetails.getPaymentStatus())
                .build();
    }
}
