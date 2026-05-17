package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ContractNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ContractResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ContractController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindContractByIdHandler implements OperationHandler {

  private final ContractController contractController;
  private final ConsoleIO console;
  private final ContractResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Contract ID: ");
    try {
      final ContractResponse contract = contractController.findContractById(id);
      printer.print(contract);
    } catch (final ContractNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
