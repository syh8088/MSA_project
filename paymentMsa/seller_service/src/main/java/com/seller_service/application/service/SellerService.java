package com.seller_service.application.service;

import com.common.UseCase;
import com.seller_service.adapter.axon.command.RequestInsertSellerCommand;
import com.seller_service.application.port.in.InsertSellerUseCase;
import com.seller_service.domain.RequestPersistenceInsertSellerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class SellerService implements InsertSellerUseCase {

    private final CommandGateway commandGateway;

    @Override
    public void insertSeller(RequestPersistenceInsertSellerCommand insertSellerCommand) {

        RequestInsertSellerCommand requestInsertSellerCommand = RequestInsertSellerCommand.of(insertSellerCommand.getSellerId());
        commandGateway.send(requestInsertSellerCommand)
        .whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        log.error("InsertSellerCommand Command failed = {}", result.toString());
                        throw new RuntimeException(throwable);
                    }
                    else {
                        log.info("InsertSellerCommand SAGA success = {}", result.toString());
                    }
                }
        );
    }
}
