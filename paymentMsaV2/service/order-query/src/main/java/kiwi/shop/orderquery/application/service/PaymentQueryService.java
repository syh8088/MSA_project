package kiwi.shop.orderquery.application.service;

import kiwi.shop.orderquery.application.port.in.PaymentQueryUseCase;
import kiwi.shop.orderquery.application.port.out.PostPaymentQueryPort;
import kiwi.shop.orderquery.client.OrderClient;
import kiwi.shop.orderquery.common.UseCase;
import kiwi.shop.orderquery.domain.PaymentEventQueryModel;
import kiwi.shop.orderquery.domain.PaymentEventResponse;
import kiwi.shop.orderquery.domain.PaymentEventResponses;
import kiwi.shop.orderquery.domain.PaymentEventWithOrderListCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PaymentQueryService implements PaymentQueryUseCase {

    private final PostPaymentQueryPort postPaymentQueryPort;

    private final OrderClient orderClient;

    @Override
    public void insertOrderQuery(PaymentEventQueryModel paymentEventQueryModel) {

        postPaymentQueryPort.insertOrderQuery(paymentEventQueryModel);
        postPaymentQueryPort.insertPaymentEventNoList(paymentEventQueryModel.getMemberNo(), paymentEventQueryModel.getPaymentEventNo(), 10);
    }

    @Override
    public PaymentEventResponse selectPaymentOrderDetail(long memberNo, long paymentEventNo) {

        PaymentEventQueryModel paymentEventQueryModel = postPaymentQueryPort.selectPaymentOrder(memberNo, paymentEventNo)
                .or(() -> this.paymentOrderFetch(memberNo, paymentEventNo)) // 없으면 호출 합니다.
                .orElseThrow();

        return PaymentEventResponse.of(paymentEventQueryModel);
    }

    @Override
    public List<PaymentEventResponse> selectPaymentEventWithOrderList(PaymentEventWithOrderListCommand paymentEventWithOrderListCommand) {

        List<Long> paymentEventNoList = this.readAllInfiniteScrollPaymentEventNoList(
                paymentEventWithOrderListCommand.getMemberNo(),
                paymentEventWithOrderListCommand.getPaymentEventNo(),
                paymentEventWithOrderListCommand.getCreatedDateTime(),
                paymentEventWithOrderListCommand.getLimit()
        );

        Map<Long, PaymentEventQueryModel> paymentEventQueryModelMap
                = postPaymentQueryPort.selectPaymentEventWithOrderList(paymentEventWithOrderListCommand.getMemberNo(), paymentEventNoList);

        return paymentEventNoList.stream()
                .map(paymentEventNo -> {

                    if (paymentEventQueryModelMap.containsKey(paymentEventNo)) {
                        return paymentEventQueryModelMap.get(paymentEventNo);
                    }
                    else {
                        return this.paymentOrderFetch(paymentEventWithOrderListCommand.getMemberNo(), paymentEventNo).orElse(null);
                    }
                })
                .filter(Objects::nonNull)
                .map(PaymentEventResponse::of)
                .toList();
    }

    private List<Long> readAllInfiniteScrollPaymentEventNoList(
            long memberNo,
            Long lastPaymentEventNo,
            LocalDateTime lastCreatedDatetime,
            int limit
    ) {

        List<Long> paymentEventNoList = postPaymentQueryPort.readAllInfiniteScroll(memberNo, lastPaymentEventNo, limit);
        if (limit == paymentEventNoList.size()) {
            log.info("[PaymentQueryService.readAllInfiniteScrollPaymentEventNoList] return redis data");
            return paymentEventNoList;
        }

        log.info("[PaymentQueryService.readAllInfiniteScrollPaymentEventNoList] return origin data");
        PaymentEventResponses paymentEventResponses = orderClient.selectPaymentOrderList(memberNo, lastPaymentEventNo, lastCreatedDatetime, limit);

        List<PaymentEventResponse> paymentEventList = paymentEventResponses.getPaymentEventList();
        return paymentEventList.stream()
                .map(PaymentEventResponse::getPaymentEventNo)
                .toList();
    }



    private Optional<PaymentEventQueryModel> paymentOrderFetch(long memberNo, long paymentEventNo) {

        PaymentEventResponse paymentEventResponse = orderClient.selectPaymentOrder(paymentEventNo, memberNo);
        Optional<PaymentEventQueryModel> optionalPaymentEventQueryModel = PaymentEventQueryModel.of(paymentEventResponse);

        optionalPaymentEventQueryModel
                .ifPresent(postPaymentQueryPort::insertOrderQuery);

        log.info("[PaymentQueryService.paymentOrderFetch] fetch data. paymentEventNo={}", paymentEventNo);

        return optionalPaymentEventQueryModel;
    }
}
