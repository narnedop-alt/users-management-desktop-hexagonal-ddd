package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.ContractAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.ContractNotFoundException;
import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ContractResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.ContractController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateContractRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateContractHandler implements OperationHandler {

  private final ContractController contractController;
  private final ConsoleIO console;
  private final ContractResponsePrinter printer;

  @Override
  public void handle() {
    final String id             = console.readRequired("Contract ID                                         : ");
    final String employeeId     = console.readRequired("Employee ID                                         : ");
    final String contractNumber = console.readRequired("Contract number                                     : ");
    final String position       = console.readRequired("Position                                            : ");
    final String contractType   = console.readRequired("Type (FIXED_TERM / INDEFINITE / SERVICE / APPRENTICESHIP): ");
    final String startDate      = console.readRequired("Start date (yyyy-MM-dd)                            : ");
    final String endDate        = console.readOptional("End date (optional)                                : ");
    final String monthlySalary  = console.readRequired("Monthly salary                                     : ");
    final String status         = console.readRequired("Status (ACTIVE / SUSPENDED / FINISHED / CANCELLED): ");

    try {
      final ContractResponse updated =
          contractController.updateContract(
              new UpdateContractRequest(
                  id, employeeId, contractNumber, position, contractType,
                  startDate, endDate, monthlySalary, status));
      console.println("\n  Contract updated successfully.");
      printer.print(updated);
    } catch (final ContractAlreadyExistsException
        | ContractNotFoundException
        | EmployeeNotFoundException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}
