package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmployeeResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmployeeController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmployeeResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateEmployeeRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateEmployeeHandler implements OperationHandler {

  private final EmployeeController employeeController;
  private final ConsoleIO console;
  private final EmployeeResponsePrinter printer;

  @Override
  public void handle() {
    final String id             = console.readRequired("Employee ID                                                : ");
    final String documentNumber = console.readRequired("Document number                                            : ");
    final String firstName      = console.readRequired("First name                                                 : ");
    final String lastName       = console.readRequired("Last name                                                  : ");
    final String email          = console.readRequired("Email                                                      : ");
    final String phone          = console.readOptional("Phone (optional)                                           : ");
    final String baseSalary     = console.readRequired("Base salary                                                : ");
    final String status         = console.readRequired("Status (ACTIVE / INACTIVE / SUSPENDED / TERMINATED)       : ");

    try {
      final EmployeeResponse updated = employeeController.updateEmployee(
          new UpdateEmployeeRequest(
              id, documentNumber, firstName, lastName, email, phone, baseSalary, status));
      console.println("\n  Employee updated successfully.");
      printer.print(updated);
    } catch (final EmployeeNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
