package com.seller_service.adapter.axon.saga;

import com.common.InsertWalletCommand;
import com.common.command.RollbackSellerRequestCommand;
import com.common.event.RequestDeleteSellerEvent;
import com.common.event.RequestInsertWalletFinishedEvent;
import com.seller_service.adapter.axon.event.SagaInsertSellerEvent;
import com.seller_service.adapter.axon.event.SagaInsertWalletEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Slf4j
@Saga
@NoArgsConstructor
public class InsertSellerAndWalletSaga {

    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(@NotNull CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "requestInsertSellerId")
    public void handle(SagaInsertSellerEvent event) {

        String sagaInsertSellerId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("sagaInsertSellerId", sagaInsertSellerId);

        commandGateway.send(
                new com.common.command.InsertSellerCommand(
                        UUID.randomUUID().toString(),
                        event.getRequestInsertSellerId(),
                        event.getSellerId(),
                        sagaInsertSellerId
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

    @SagaEventHandler(associationProperty = "sagaInsertSellerId")
    public void handle(SagaInsertWalletEvent event) {

        String sagaInsertWalletId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("sagaInsertWalletId", sagaInsertWalletId);

        commandGateway.send(new InsertWalletCommand(
                UUID.randomUUID().toString(),
                sagaInsertWalletId,
                event.getSagaInsertSellerId(),
                event.getSellerNo(),
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

    @SagaEventHandler(associationProperty = "sagaInsertWalletId")
    public void handle(RequestInsertWalletFinishedEvent event) {

        boolean isSuccess = event.isSuccess();
        if (isSuccess) {
            SagaLifecycle.end();
        }
        else {
            // 실패 시, 롤백 이벤트
            String sagaRollbackSellerId = UUID.randomUUID().toString();
            SagaLifecycle.associateWith("sagaRollbackSellerId", sagaRollbackSellerId);
            commandGateway.send(new RollbackSellerRequestCommand(
                    sagaRollbackSellerId,
                    event.getSellerNo()
            )).whenComplete(
                    (result, throwable) -> {
                        if (throwable != null) {
                            throwable.printStackTrace();
                            System.out.println("RollbackSellerRequestCommand Command failed");
                        } else {
                            System.out.println("Saga success : "+ result.toString());
                            SagaLifecycle.end();
                        }
                    }
            );
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "sagaRollbackSellerId")
    public void handle(RequestDeleteSellerEvent event) {
        log.info("EndSaga >> requestDeleteSellerId = {}", event.getRequestDeleteSellerId());
    }
}
