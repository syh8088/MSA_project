package kiwi.shop.order.application.service;

import kiwi.shop.order.application.port.in.RequestOrderUseCase;
import kiwi.shop.order.application.port.out.GetOrderPort;
import kiwi.shop.order.common.UseCase;
import kiwi.shop.order.domain.PaymentEventOutPut;
import kiwi.shop.order.domain.PaymentEventWithOrderListCommand;
import kiwi.shop.order.domain.PaymentEventWithOrderOutPut;
import kiwi.shop.order.domain.PaymentOrderWithSellerOutPut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class OrderService implements RequestOrderUseCase {

    private final GetOrderPort getOrderPort;

    @Transactional(readOnly = true)
    @Override
    public List<PaymentOrderWithSellerOutPut> selectPaymentOrderListWithSellerByOrderId(String orderId) {
        return getOrderPort.selectPaymentOrderListWithSellerByOrderId(orderId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<PaymentEventOutPut> selectPaymentEventByMemberNoAndPaymentMethodNo(long memberNo, long paymentMethodNo) {
        return getOrderPort.selectPaymentEventByMemberNoAndPaymentMethodNo(memberNo, paymentMethodNo);
    }

    @Transactional(readOnly = true)
    @Override
    public void selectPaymentOrderList(long memberNo) {
        getOrderPort.selectPaymentEventList(memberNo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNo(PaymentEventWithOrderListCommand paymentEventWithOrderListCommand) {
        return getOrderPort.selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(paymentEventWithOrderListCommand);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(long memberNo, long paymentEventNo) {

        return getOrderPort.selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(memberNo, paymentEventNo);
    }
}
