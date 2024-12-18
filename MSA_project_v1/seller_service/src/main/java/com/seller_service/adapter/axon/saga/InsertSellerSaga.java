package com.seller_service.adapter.axon.saga;

//import com.common.InsertWalletCommandV2;
//import com.common.event.RequestInsertWalletFinishedEvent;
import com.seller_service.adapter.axon.command.InsertWalletCommand;
import com.seller_service.adapter.axon.event.AxonInsertSellerEvent;
import com.seller_service.adapter.axon.event.RequestInsertWalletFinishedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@NoArgsConstructor
public class InsertSellerSaga {

    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(@NotNull CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "requestInsertSellerId")
    public void handle(AxonInsertSellerEvent event) {
        System.out.println("AxonInsertSellerEvent Start saga");

        String requestInsertWalletId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("requestInsertWalletId", requestInsertWalletId);

        // "충전 요청" 이 시작 되었다.

        // 뱅킹의 계좌 등록 여부 확인하기. (RegisteredBankAccount)
        // CheckRegisteredBankAccountCommand -> Check Bank Account
        // -> axon server -> Banking Service -> Common


        // 기본적으로 axon framework 에서, 모든 aggregate 의 변경은, aggregate 단위로 되어야만 한다.
        commandGateway.send(new InsertWalletCommand(
                UUID.randomUUID().toString(),
                requestInsertWalletId,
                1,
                event.getSellerId()
            )
        ).whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        System.out.println("CheckRegisteredBankAccountCommand Command failed");
                    } else {
                        System.out.println("CheckRegisteredBankAccountCommand Command success");
                    }
                }
        );
    }


    @SagaEventHandler(associationProperty = "requestInsertWalletId")
    public void handle(RequestInsertWalletFinishedEvent event) {
        System.out.println("RequestInsertWalletFinishedEvent saga: " + event.toString());

        SagaLifecycle.end();


//        if (resultEntity == null) {
//            // 실패 시, 롤백 이벤트
//            String rollbackFirmbankingId = UUID.randomUUID().toString();
//            SagaLifecycle.associateWith("rollbackFirmbankingId", rollbackFirmbankingId);
//            commandGateway.send(new com.fastcampuspay.common.command.RollbackFirmbankingRequestCommand(
//                    rollbackFirmbankingId
//                    ,event.getRequestFirmbankingAggregateIdentifier()
//                    , event.getRechargingRequestId()
//                    , event.getMembershipId()
//                    , event.getToBankName()
//                    , event.getToBankAccountNumber()
//                    , event.getMoneyAmount()
//            )).whenComplete(
//                    (result, throwable) -> {
//                        if (throwable != null) {
//                            throwable.printStackTrace();
//                            System.out.println("RollbackFirmbankingRequestCommand Command failed");
//                        } else {
//                            System.out.println("Saga success : "+ result.toString());
//                            SagaLifecycle.end();
//                        }
//                    }
//            );
//        } else {
//            // 성공 시, saga 종료.
//            SagaLifecycle.end();
//        }
    }
}
