package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ContractResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ContractController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListContractsHandler implements OperationHandler {

  private final ContractController contractController;
  private final ContractResponsePrinter printer;

  @Override
  public void handle() {
    final List<ContractResponse> contracts = contractController.listAllContracts();
    printer.printList(contracts);
  }
}
