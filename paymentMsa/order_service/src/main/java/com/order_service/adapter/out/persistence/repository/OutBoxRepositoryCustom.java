package com.order_service.adapter.out.persistence.repository;


import com.order_service.domain.OutBoxOutPut;
import com.order_service.domain.OutBoxStatus;

import java.util.List;

public interface OutBoxRepositoryCustom {

    List<OutBoxOutPut> selectOutBoxPendingEventMessageList(OutBoxStatus outBoxStatus);
}
