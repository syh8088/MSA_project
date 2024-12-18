package com.seller_service.application.service;

import com.common.UseCase;
import com.seller_service.adapter.axon.command.AxonInsertSellerCommand;
import com.seller_service.application.port.in.InsertSellerUseCase;
import com.seller_service.application.port.out.InsertSellerPort;
import com.seller_service.domain.InsertSellerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;

import java.util.UUID;


@Slf4j
@UseCase
@RequiredArgsConstructor
public class SellerService implements InsertSellerUseCase {

    private final InsertSellerPort insertSellerPort;
    private final CommandGateway commandGateway;

    @Override
    public void insertSeller(InsertSellerCommand insertSellerCommand) {

        AxonInsertSellerCommand axonInsertSellerCommand = AxonInsertSellerCommand.of(insertSellerCommand.getSellerId());
        commandGateway.send(axonInsertSellerCommand)
        .whenComplete(
                (result, throwable) -> {
                    if (throwable != null) {
                        throwable.printStackTrace();
                        throw new RuntimeException(throwable);
                    } else {
                        System.out.println("result = " + result); // aggregateIdentifier
                    }
                }
        );

//        insertSellerPort.insertSeller(insertSellerCommand);

        System.out.println("insertSellerCommand = " + insertSellerCommand);
    }
}
