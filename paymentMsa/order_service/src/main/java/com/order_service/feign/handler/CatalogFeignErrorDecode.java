package com.order_service.feign.handler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CatalogFeignErrorDecode {
//        implements ErrorDecoder {
//
//    private final Gson gson = JsonUtils.getPrettyGson();
//
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        final HttpStatus.Series series = HttpStatus.valueOf(response.status()).series();
//
//        final String requestBody = FeignResponseUtils.getRequestBody(response);
//        final String responseBody = FeignResponseUtils.getResponseBody(response);
//
//        this.exception4xxCheck(response.status(), responseBody);
//
//        final JsonObject requestBodyJson = stringToJson(requestBody);
//        final JsonObject responseBodyJson = stringToJson(responseBody);
//
//        log.error(":: 결제 오류 메소드                     \t =>    {} ::", methodKey);
//        log.info(":: [API Server -> Toss] Request       \t => \n {} ::", gson.toJson(requestBodyJson));
//        log.info(":: [API Server <- Toss] Response      \t => \n {} ::", gson.toJson(responseBodyJson));
//
//        final TossFailureResponse paymentErrorResponse = gson.fromJson(responseBodyJson, TossFailureResponse.class);
//
//        switch (series) {
//            case CLIENT_ERROR:
//                log.error(":: [클라이언트] 결제 오류 URI     \t => {} ::", response.request().url());
//                log.error(":: [클라이언트] 결제 오류 내용     \t => {} ::", paymentErrorResponse.getMessage());
//                log.error(":: [클라이언트] 결제 오류 코드     \t => {} ::", paymentErrorResponse.getCode());
//
//                throw new PaymentException(PSPErrorCode.CLIENT_ERROR, paymentErrorResponse.getCode(), new String[]{paymentErrorResponse.getMessage()}, responseBody);
//
//            case SERVER_ERROR:
//                log.error(":: [서버]     결제 요청 URI     \t => {} ::", response.request().url());
//                log.error(":: [서버]     결제 오류 내용     \t => {} ::", paymentErrorResponse.getMessage() );
//                log.error(":: [서버]     결제 오류 코드     \t => {} ::", paymentErrorResponse.getCode());
//
//                throw new PaymentException(PSPErrorCode.SERVER_ERROR, paymentErrorResponse.getCode(), new String[]{paymentErrorResponse.getMessage()}, responseBody);
//
//            default:
//                log.error(":: [서버]     결제 요청 Default \t => {} ::", "정의되지 않은 오류.");
//                log.error(":: [서버]     결제 요청 URI     \t => {} ::", response.request().url());
//                log.error(":: [서버]     결제 오류 내용     \t => {} ::", paymentErrorResponse.getMessage() );
//                log.error(":: [서버]     결제 오류 코드     \t => {} ::", paymentErrorResponse.getCode());
//
//                throw new PaymentException(PSPErrorCode.SERVER_ERROR, paymentErrorResponse.getCode(), new String[]{paymentErrorResponse.getMessage()}, responseBody);
//        }
//    }
//
//    private void exception4xxCheck(int responseStatusCode, String responseBody) {
//        final HttpStatus responseStatus = HttpStatus.valueOf(responseStatusCode);
//
//        if ((HttpStatus.UNAUTHORIZED == responseStatus) && StringUtils.isBlank(responseBody)) {
//            throw new PaymentException(PSPErrorCode.CLIENT_ERROR, new String[]{"접근 권한이 없습니다."});
//        }
//
//    }
//
//    private JsonObject stringToJson(String json) {
//        try {
//            return gson.fromJson(json, JsonObject.class);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//        return null;
//    }
//
//    @Getter
//    @ToString
//    static class PaymentErrorResponse {
//        private String message;
//        private String developerMessage;
//    }
}