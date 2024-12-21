package com.order_query_service.application.port.in;


import com.order_query_service.adapter.in.web.response.OrderTotalAmountGroupingProduct;

import java.util.List;

public interface GetOrderQueryUseCase {


    List<OrderTotalAmountGroupingProduct> selectOrderTotalAmountGroupingProductBySellerNo(long sellerNo);

}
