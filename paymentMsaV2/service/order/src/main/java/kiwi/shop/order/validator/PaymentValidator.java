package kiwi.shop.order.validator;

import kiwi.shop.order.application.port.out.ProductOutPut;
import kiwi.shop.order.adapter.out.web.error.errorCode.PaymentErrorCode;
import kiwi.shop.order.adapter.out.web.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentValidator {


    public void isExistProduct(List<ProductOutPut> productList) {

        if (Objects.isNull(productList) || productList.isEmpty()) {
            throw new BusinessException(PaymentErrorCode.NOT_EXIST_PRODUCT);
        }
    }

//    public void paymentTotalAmountValidation(String orderId, String requestTotalAmount) {
//        BigDecimal totalAmount = paymentOrderQueryService.selectTotalAmountByOrderId(orderId);
//
//        if (totalAmount.compareTo(new BigDecimal(requestTotalAmount)) != 0) {
//            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
//        }
//    }
}
