package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmployeeResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmployeeController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmployeeResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindEmployeeByIdHandler implements OperationHandler {

  private final EmployeeController employeeController;
  private final ConsoleIO console;
  private final EmployeeResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Employee ID: ");
    try {
      final EmployeeResponse employee = employeeController.findEmployeeById(id);
      printer.print(employee);
    } catch (final EmployeeNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
