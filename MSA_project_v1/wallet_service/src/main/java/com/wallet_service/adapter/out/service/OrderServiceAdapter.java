package com.wallet_service.adapter.out.service;

import com.common.WebAdapter;
import com.wallet_service.adapter.out.client.OrderClient;
import com.wallet_service.application.port.out.GetPaymentOrderPort;
import com.wallet_service.domain.PaymentOrderWithSellerOutPut;
import com.wallet_service.domain.PaymentOrderWithSellerResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class OrderServiceAdapter implements GetPaymentOrderPort {

    private final OrderClient orderClient;

    @Override
    public List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId) {

        PaymentOrderWithSellerResponses paymentOrderWithSellerResponses = orderClient.selectPaymentOrderListWithSellerByOrderId(orderId);
        return PaymentOrderWithSellerOutPut.of(paymentOrderWithSellerResponses.getPaymentOrderList());
    }
}
