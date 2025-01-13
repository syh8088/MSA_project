package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.common.event.domain.PaymentEventWithOrderOutPut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface PaymentEventMapper {

	List<PaymentEventWithOrderOutPut> selectFirstPaymentEventWithOrderListByMemberNo(
			@Param("memberNo") long memberNo,
			@Param("limit") int limit

	);

	List<PaymentEventWithOrderOutPut> selectPaymentEventWithOrderListByMemberNoAndPaymentEventNo(
			@Param("paymentEventNo") long paymentEventNo,
			@Param("createdDateTime") LocalDateTime createdDateTime,
			@Param("memberNo") long memberNo,
			@Param("limit") int limit

	);
}
