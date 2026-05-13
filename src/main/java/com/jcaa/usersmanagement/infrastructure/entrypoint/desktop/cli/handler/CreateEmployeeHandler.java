package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.EmployeeAlreadyExistsException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmployeeResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmployeeController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateEmployeeRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmployeeResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateEmployeeHandler implements OperationHandler {

  private final EmployeeController employeeController;
  private final ConsoleIO console;
  private final EmployeeResponsePrinter printer;

  @Override
  public void handle() {
    final String id             = console.readRequired("ID                          : ");
    final String documentNumber = console.readRequired("Document number             : ");
    final String firstName      = console.readRequired("First name                  : ");
    final String lastName       = console.readRequired("Last name                   : ");
    final String email          = console.readRequired("Email                       : ");
    final String phone          = console.readOptional("Phone (optional)            : ");
    final String baseSalary     = console.readRequired("Base salary                 : ");

    try {
      final EmployeeResponse created = employeeController.createEmployee(
          new CreateEmployeeRequest(id, documentNumber, firstName, lastName, email, phone, baseSalary));
      console.println("\n  Employee created successfully.");
      printer.print(created);
    } catch (final EmployeeAlreadyExistsException exception) {
      console.println("  Error: " + exception.getMessage());
    }
  }
}
