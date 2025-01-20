package kiwi.shop.orderquery.cache;

@FunctionalInterface
public interface OptimizedCacheOriginDataSupplier<T> {
    T get() throws Throwable;
}
