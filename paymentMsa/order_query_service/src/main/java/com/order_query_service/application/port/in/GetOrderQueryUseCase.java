package com.order_query_service.application.port.in;


import com.order_query_service.domain.PaymentOrderWithSellerOutPut;

import java.util.List;

public interface GetOrderQueryUseCase {


    List<PaymentOrderWithSellerOutPut> selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo);

}
