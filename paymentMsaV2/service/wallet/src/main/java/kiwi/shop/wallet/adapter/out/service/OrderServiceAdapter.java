package kiwi.shop.wallet.adapter.out.service;

import kiwi.shop.wallet.adapter.out.client.OrderClient;
import kiwi.shop.wallet.application.port.out.GetPaymentOrderPort;
import kiwi.shop.wallet.common.WebAdapter;
import kiwi.shop.wallet.domain.PaymentOrderWithSellerOutPut;
import kiwi.shop.wallet.domain.PaymentOrderWithSellerResponses;
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
