package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ContractNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ContractResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ContractController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FinishContractHandler implements OperationHandler {

  private final ContractController contractController;
  private final ConsoleIO console;
  private final ContractResponsePrinter printer;

  @Override
  public void handle() {
    final String id      = console.readRequired("Contract ID to finish    : ");
    final String endDate = console.readRequired("End date (yyyy-MM-dd)    : ");
    try {
      final ContractResponse finished = contractController.finishContract(id, endDate);
      console.println("  Contract finished successfully.");
      printer.print(finished);
    } catch (final ContractNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
