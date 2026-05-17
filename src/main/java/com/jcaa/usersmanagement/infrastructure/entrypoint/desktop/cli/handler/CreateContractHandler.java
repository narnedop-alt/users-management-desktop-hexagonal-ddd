package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ContractAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ContractResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ContractController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateContractRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateContractHandler implements OperationHandler {

  private final ContractController contractController;
  private final ConsoleIO console;
  private final ContractResponsePrinter printer;

  @Override
  public void handle() {
    final String employeeId     = console.readRequired("Employee ID                  : ");
    final String contractNumber = console.readRequired("Contract number             : ");
    final String position       = console.readRequired("Position                    : ");
    final String contractType   = console.readRequired("Type (FIXED_TERM / INDEFINITE / SERVICE / APPRENTICESHIP): ");
    final String startDate      = console.readRequired("Start date (yyyy-MM-dd)     : ");
    final String endDate        = console.readOptional("End date (optional)         : ");
    final String monthlySalary  = console.readRequired("Monthly salary              : ");

    try {
      final ContractResponse created =
          contractController.createContract(
              new CreateContractRequest(
                  employeeId, contractNumber, position, contractType, startDate, endDate, monthlySalary));
      console.println("\n  Contract created successfully.");
      printer.print(created);
    } catch (final ContractAlreadyExistsException | EmployeeNotFoundException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}
