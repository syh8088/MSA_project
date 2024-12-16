package com.order_service.adapter.out.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPaymentEvent is a Querydsl query type for PaymentEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentEvent extends EntityPathBase<PaymentEvent> {

    private static final long serialVersionUID = -1059431782L;

    public static final QPaymentEvent paymentEvent = new QPaymentEvent("paymentEvent");

    public final QCommonEntity _super = new QCommonEntity(this);

    public final DateTimePath<java.time.LocalDateTime> approvedDateTime = createDateTime("approvedDateTime", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    public final BooleanPath isPaymentDone = createBoolean("isPaymentDone");

    public final BooleanPath isWalletDone = createBoolean("isWalletDone");

    public final EnumPath<com.order_service.domain.PaymentEventMethod> method = createEnum("method", com.order_service.domain.PaymentEventMethod.class);

    public final NumberPath<Long> no = createNumber("no", Long.class);

    public final StringPath orderId = createString("orderId");

    public final StringPath orderName = createString("orderName");

    public final StringPath paymentKey = createString("paymentKey");

    public final ListPath<PaymentOrder, QPaymentOrder> paymentOrderList = this.<PaymentOrder, QPaymentOrder>createList("paymentOrderList", PaymentOrder.class, QPaymentOrder.class, PathInits.DIRECT2);

    public final StringPath pspRawData = createString("pspRawData");

    public final EnumPath<com.order_service.domain.PaymentEventType> type = createEnum("type", com.order_service.domain.PaymentEventType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QPaymentEvent(String variable) {
        super(PaymentEvent.class, forVariable(variable));
    }

    public QPaymentEvent(Path<? extends PaymentEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPaymentEvent(PathMetadata metadata) {
        super(PaymentEvent.class, metadata);
    }

}

