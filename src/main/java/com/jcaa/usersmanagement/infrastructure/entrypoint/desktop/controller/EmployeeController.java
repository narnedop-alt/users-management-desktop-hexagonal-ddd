package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateEmployeeUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteEmployeeUseCase;
import com.jcaa.usersmanagement.application.port.in.FindEmployeeByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.ListEmployeesUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateEmployeeUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateEmployeeRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmployeeResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateEmployeeRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.EmployeeDesktopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class EmployeeController {

  private final CreateEmployeeUseCase createEmployeeUseCase;
  private final UpdateEmployeeUseCase updateEmployeeUseCase;
  private final DeleteEmployeeUseCase deleteEmployeeUseCase;
  private final FindEmployeeByIdUseCase findEmployeeByIdUseCase;
  private final ListEmployeesUseCase listEmployeesUseCase;

  public List<EmployeeResponse> listAllEmployees() {
    final var employees = listEmployeesUseCase.execute();
    return EmployeeDesktopMapper.toResponseList(employees);
  }

  public EmployeeResponse findEmployeeById(final String id) {
    final var query = EmployeeDesktopMapper.toFindByIdQuery(id);
    final var employee = findEmployeeByIdUseCase.execute(query);
    return EmployeeDesktopMapper.toResponse(employee);
  }

  public EmployeeResponse createEmployee(final CreateEmployeeRequest request) {
    final var command = EmployeeDesktopMapper.toCreateCommand(request);
    final var employee = createEmployeeUseCase.execute(command);
    return EmployeeDesktopMapper.toResponse(employee);
  }

  public EmployeeResponse updateEmployee(final UpdateEmployeeRequest request) {
    final var command = EmployeeDesktopMapper.toUpdateCommand(request);
    final var employee = updateEmployeeUseCase.execute(command);
    return EmployeeDesktopMapper.toResponse(employee);
  }

  public void deleteEmployee(final String id) {
    final var command = EmployeeDesktopMapper.toDeleteCommand(id);
    deleteEmployeeUseCase.execute(command);
  }
}
