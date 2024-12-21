package com.order_query_service.adapter.out.persistence.service;

import com.common.WebAdapter;
import com.order_query_service.adapter.in.web.response.OrderTotalAmountGroupingProduct;
import com.order_query_service.adapter.out.persistence.repository.PaymentOrderRedisRepository;
import com.order_query_service.application.port.out.PaymentOrderRedisPort;
import com.order_query_service.domain.PaymentOrderWithSellerOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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

            Map<Long, List<OrderTotalAmountGroupingProduct>> savedOrderSellerTotalAmountGroupingProductNoMap
                    = this.createOrderSellerTotalAmountGroupingProductNoMap(sellerNo);

            List<PaymentOrderWithSellerOutPut> paymentOrderList = paymentOrderWithSellerGroupingSellerNoMap.get(sellerNo);
            Map<Long, List<PaymentOrderWithSellerOutPut>> paymentOrderWithSellerGroupingProductNoMap = paymentOrderList.stream()
                    .collect(Collectors.groupingBy(PaymentOrderWithSellerOutPut::getProductNo));

            for (long productNo : paymentOrderWithSellerGroupingProductNoMap.keySet()) {

                List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerOutPuts = paymentOrderWithSellerGroupingProductNoMap.get(productNo);
                double totalAmount = this.calculateTotalAmount(paymentOrderWithSellerOutPuts);

                double savedProductTotalAmount = this.getSavedProductTotalAmount(productNo, savedOrderSellerTotalAmountGroupingProductNoMap);

                paymentOrderRedisRepository.updatePaymentOrderAmountSumGropingSellerNo(sellerNo, productNo, savedProductTotalAmount + totalAmount);
            }
        }
    }


    @Override
    public List<OrderTotalAmountGroupingProduct> selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo) {

        Set<ZSetOperations.TypedTuple<String>> typedTuples
                = paymentOrderRedisRepository.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);

        return typedTuples.stream()
                .map(data -> OrderTotalAmountGroupingProduct.of(data.getScore(), data.getValue()))
                .toList();
    }

    private double calculateTotalAmount(List<PaymentOrderWithSellerOutPut> paymentOrderWithSellerOutPuts) {
        return paymentOrderWithSellerOutPuts.stream()
                .mapToDouble(data -> data.getAmount().doubleValue())
                .sum();
    }

    private double getSavedProductTotalAmount(
            long productNo,
            Map<Long, List<OrderTotalAmountGroupingProduct>> savedOrderSellerTotalAmountGroupingProductNoMap
    ) {

        List<OrderTotalAmountGroupingProduct> list
                = savedOrderSellerTotalAmountGroupingProductNoMap.get(productNo);
        return OrderTotalAmountGroupingProduct.calculateTotalAmount(list);
    }

    private @NotNull Map<Long, List<OrderTotalAmountGroupingProduct>> createOrderSellerTotalAmountGroupingProductNoMap(long sellerNo) {

        List<OrderTotalAmountGroupingProduct> orderTotalAmountGroupingProductRespons
                = this.selectOrderTotalAmountGroupingProductBySellerNo(sellerNo);

        Map<Long, List<OrderTotalAmountGroupingProduct>> orderSellerTotalAmountGroupingProductNoMap
                = orderTotalAmountGroupingProductRespons.stream()
                .collect(Collectors.groupingBy(OrderTotalAmountGroupingProduct::getProductNo));

        return orderSellerTotalAmountGroupingProductNoMap;
    }
}
