package kiwi.shop.order.adapter.out.web.toss.executor;

import com.google.gson.JsonObject;
import kiwi.shop.order.adapter.out.web.error.exception.PaymentException;
import kiwi.shop.order.client.TossPaymentClient;
import kiwi.shop.order.common.JsonUtils;
import kiwi.shop.order.domain.PaymentConfirmCommand;
import kiwi.shop.order.domain.PaymentExecutionResultOutPut;
import kiwi.shop.order.domain.properties.TossPaymentProperties;
import kiwi.shop.order.domain.toss.TossFailureResponse;
import kiwi.shop.order.domain.toss.TossPaymentConfirmationResponse;
import kiwi.shop.order.domain.toss.TossPaymentConfirmationWithPspRawDataResponse;
import kiwi.shop.order.domain.toss.TossPaymentError;
import kiwi.shop.order.adapter.out.web.error.exception.PSPConfirmationException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@RequiredArgsConstructor
public class TossPaymentExecutor implements PaymentExecutor {


    private final TossPaymentClient tossPaymentClient;
    private final TossPaymentProperties tossPaymentProperties;

    private static final String PAYMENT_RETRY_CONFIG = "paymentRetryConfig";

    @Retry(name = PAYMENT_RETRY_CONFIG, fallbackMethod = "fallbackPaymentExecutor")
    @Override
    public PaymentExecutionResultOutPut paymentExecutor(PaymentConfirmCommand command) {

        TossPaymentConfirmationWithPspRawDataResponse response = this.feignPayment(command);
        return PaymentExecutionResultOutPut.of(command, response);
    }

    /**
     * resilience4j maxAttempts 설정한 횟수 전부 실패시 fallback 이 실행
     */
    private PaymentExecutionResultOutPut fallbackPaymentExecutor(PaymentConfirmCommand command, Exception exception) {
        log.error("fallbackPaymentExecutor! your request is {}, errorMessage={}", command, exception.getMessage());

        String pspRawData = this.extractPspRawData(exception);
        TossFailureResponse tossFailureResponse = TossFailureResponse.of(exception);

        /**
         * 결제 승인 응답이 만약에 "400	ALREADY_PROCESSED_PAYMENT - '이미 처리된 결제 입니다.'" (Toss 전용) 발생 하면
         * 조회 해서 결제 조회 통해 응답에 따라 다시 결과 처리 할 수도록 합니다.
         *
         * 이러한 조치를 취하는 이유는 실질적으로 효성으로 부터 결제 승인을 처리가 완료되었는데 이후
         * 서버 시스템이 장애가 발생되거나 결제 상태 업데이트 처리 하는 과정에서 Exception 발생시 대비 하기 위함
         * 그래서 해당 {@link com.payment_service.domain.payment.model.entity.PaymentEvent#paymentKey} Toss 결제의 키 기준으로 다시 결제 호출시에도
         * 2번 결제승인 처리 하지 않고 상태 업데이트만 처리 하기 위함
         */
        TossPaymentError tossPaymentError = TossPaymentError.get(tossFailureResponse.getErrorCode());
        if (tossPaymentError.isAlreadyProcessedPayment()) {

            String paymentKey = command.getPaymentKey();
            TossPaymentConfirmationResponse tossPaymentConfirmationResponse = this.feignGetPayment(paymentKey);
            TossPaymentConfirmationWithPspRawDataResponse response
                    = TossPaymentConfirmationWithPspRawDataResponse.of(tossPaymentConfirmationResponse, pspRawData);

            return PaymentExecutionResultOutPut.of(command, response);
        }

        TossPaymentConfirmationWithPspRawDataResponse response
                = TossPaymentConfirmationWithPspRawDataResponse.of(command, tossFailureResponse, pspRawData);

        return PaymentExecutionResultOutPut.of(command, response);
    }

    /**
     * <h1><Toss> 결제 승인 조회</h1> </br>
     **/
    public TossPaymentConfirmationResponse feignGetPayment(String paymentKey) {

        final ResponseEntity<String> getPaymentResponse = tossPaymentClient.getPayments(paymentKey);
        final JsonObject jsonObject = JsonUtils.fromJson(getPaymentResponse.getBody(), JsonObject.class);

        return JsonUtils.fromJson(jsonObject.toString(), TossPaymentConfirmationResponse.class);
    }

    /**
     * <h1><Toss> 카드 승인</h1> </br>
     **/
    private TossPaymentConfirmationWithPspRawDataResponse feignPayment(PaymentConfirmCommand request) {

        final ResponseEntity<String> paymentsResponse
                = tossPaymentClient.paymentConfirm(request.getOrderId(), request);

        // 재시도가 불가능한 실패
//        final ResponseEntity<String> paymentsResponse
//                = tossPaymentClient.paymentError(request.getOrderId(), "ALREADY_COMPLETED_PAYMENT", request);


        // 재시도가 가능한 실패
//        final ResponseEntity<String> paymentsResponse
//                = tossPaymentClient.paymentError(request.getOrderId(), "ALREADY_PROCESSED_PAYMENT", request);

        final JsonObject jsonObject = JsonUtils.fromJson(paymentsResponse.getBody(), JsonObject.class);

        String responseJson = jsonObject.toString();
        TossPaymentConfirmationResponse tossPaymentConfirmationResponse = JsonUtils.fromJson(responseJson, TossPaymentConfirmationResponse.class);

        return TossPaymentConfirmationWithPspRawDataResponse.of(tossPaymentConfirmationResponse, responseJson);
    }

    private String extractPspRawData(Exception exception) {

        String pspRawData = null;

        if (exception instanceof PaymentException) {
            PaymentException paymentException = (PaymentException) exception;
            pspRawData = paymentException.getPspRawData();
        }

        if (exception instanceof PSPConfirmationException) {
            PSPConfirmationException pspConfirmationException = (PSPConfirmationException) exception;
            return pspConfirmationException.toJson();
        }

        return pspRawData;
    }


}
