package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmployeeController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteEmployeeHandler implements OperationHandler {

  private final EmployeeController employeeController;
  private final ConsoleIO console;

  @Override
  public void handle() {
    final String id = console.readRequired("Employee ID to delete: ");
    try {
      employeeController.deleteEmployee(id);
      console.println("  Employee deleted successfully.");
    } catch (final EmployeeNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
