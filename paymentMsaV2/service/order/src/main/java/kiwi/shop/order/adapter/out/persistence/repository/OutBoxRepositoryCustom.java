package kiwi.shop.order.adapter.out.persistence.repository;


import kiwi.shop.order.domain.OutBoxOutPut;
import kiwi.shop.order.domain.OutBoxStatus;

import java.util.List;

public interface OutBoxRepositoryCustom {

    List<OutBoxOutPut> selectOutBoxPendingEventMessageList(OutBoxStatus outBoxStatus);
}
