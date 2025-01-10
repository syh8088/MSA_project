package kiwi.shop.wallet.adapter.out.exception;

public class RetryExhaustedWithOptimisticLockingFailureException extends RuntimeException {

    public RetryExhaustedWithOptimisticLockingFailureException(String message) {
        super(message);
    }
}