package kiwi.shop.catalogreview.adapter.out.persistence.exception;

public class RetryExhaustedWithOptimisticLockingFailureException extends RuntimeException {

    public RetryExhaustedWithOptimisticLockingFailureException(String message) {
        super(message);
    }
}