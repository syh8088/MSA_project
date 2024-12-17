package com.order_service.adapter.out.web.error.errorCode;

public interface ErrorCode {
    String getCode();
    String getCodePath();
    int getHttpStatus();
}
