package com.order_service.error.errorCode;

public interface ErrorCode {
    String getCode();
    String getCodePath();
    int getHttpStatus();
}
