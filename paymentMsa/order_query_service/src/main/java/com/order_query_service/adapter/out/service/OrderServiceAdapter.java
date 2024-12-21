package com.order_query_service.adapter.out.service;

import com.common.WebAdapter;
import com.order_query_service.adapter.in.web.response.PaymentOrderWithSellerResponses;
import com.order_query_service.adapter.out.client.OrderClient;
import com.order_query_service.application.port.out.GetPaymentOrderPort;
import com.order_query_service.domain.PaymentOrderWithSellerOutPut;
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
