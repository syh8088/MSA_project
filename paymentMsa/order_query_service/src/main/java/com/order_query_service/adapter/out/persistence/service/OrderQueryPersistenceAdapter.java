package com.order_query_service.adapter.out.persistence.service;

import com.common.WebAdapter;
import com.order_query_service.adapter.in.web.response.OrderTotalAmountGroupingProductResponse;
import com.order_query_service.adapter.out.persistence.repository.PaymentOrderRedisRepository;
import com.order_query_service.application.port.out.PaymentOrderRedisPort;
import com.order_query_service.domain.PaymentOrderWithSellerOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@WebAdapter
@RequiredArgsConstructor
public class OrderQueryPersistenceAdapter implements PaymentOrderRedisPort {

    private final PaymentOrderRedisRepository paymentOrderRedisRepository;

    @Override
    public void updatePaymentOrderAmountSumGropingSellerNo(String orderId, List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerList) {

        // 결제 주문 데이터에 저장되어 있는 판매자 ID 를 바탕으로 판매자 지갑대를 조회하는 로직
        Map<Long, List<PaymentOrderWithSellerOutPut>> paymentOrderWithSellerGroupingSellerNoMap
                = paymentOrderWithSellerList.stream()
                .collect(Collectors.groupingBy(PaymentOrderWithSellerOutPut::getSellerNo));

        for (long sellerNo : paymentOrderWithSellerGroupingSellerNoMap.keySet()) {

            List<OrderTotalAmountGroupingProductResponse> orderTotalAmountGroupingProductResponses
                    = this.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);

            Map<Long, List<OrderTotalAmountGroupingProductResponse>> orderSellerTotalAmountGroupingProductNoMap
                    = orderTotalAmountGroupingProductResponses.stream()
                    .collect(Collectors.groupingBy(OrderTotalAmountGroupingProductResponse::getProductNo));

            List<PaymentOrderWithSellerOutPut> paymentOrderList = paymentOrderWithSellerGroupingSellerNoMap.get(sellerNo);
            Map<Long, List<PaymentOrderWithSellerOutPut>> paymentOrderWithSellerGroupingProductNoMap = paymentOrderList.stream()
                    .collect(Collectors.groupingBy(PaymentOrderWithSellerOutPut::getProductNo));

            for (long productNo : paymentOrderWithSellerGroupingProductNoMap.keySet()) {
                List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerOutPuts = paymentOrderWithSellerGroupingProductNoMap.get(productNo);
                double totalAmount = this.calculateTotalAmount(paymentOrderWithSellerOutPuts);

                List<OrderTotalAmountGroupingProductResponse> list
                        = orderSellerTotalAmountGroupingProductNoMap.get(productNo);
                double productTotalAmount = OrderTotalAmountGroupingProductResponse.calculateTotalAmount(list);

                paymentOrderRedisRepository.updatePaymentOrderAmountSumGropingSellerNo(sellerNo, productNo, productTotalAmount + totalAmount);
            }
        }
    }

    @Override
    public List<OrderTotalAmountGroupingProductResponse> selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo) {

        Set<ZSetOperations.TypedTuple<String>> strings
                = paymentOrderRedisRepository.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);

        List<OrderTotalAmountGroupingProductResponse> orderTotalAmountGroupingProductResponses = strings.stream()
                .map(data -> OrderTotalAmountGroupingProductResponse.of(data.getScore(), data.getValue()))
                .toList();

       return orderTotalAmountGroupingProductResponses;
    }

    private double calculateTotalAmount(List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerOutPuts) {
        return paymentOrderWithSellerOutPuts.stream()
                .mapToDouble(data -> data.getAmount().doubleValue())
                .sum();
    }
}
