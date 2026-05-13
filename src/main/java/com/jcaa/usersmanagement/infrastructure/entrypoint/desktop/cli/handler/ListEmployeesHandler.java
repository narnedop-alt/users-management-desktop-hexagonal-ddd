package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.EmployeeResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.EmployeeController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmployeeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListEmployeesHandler implements OperationHandler {

  private final EmployeeController employeeController;
  private final EmployeeResponsePrinter printer;

  @Override
  public void handle() {
    final List<EmployeeResponse> employees = employeeController.listAllEmployees();
    printer.printList(employees);
  }
}
