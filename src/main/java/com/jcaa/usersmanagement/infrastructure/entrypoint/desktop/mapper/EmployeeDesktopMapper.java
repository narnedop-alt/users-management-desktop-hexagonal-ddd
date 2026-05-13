package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEmployeeCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEmployeeCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEmployeeCommand;
import com.jcaa.usersmanagement.application.service.dto.query.FindEmployeeByIdQuery;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateEmployeeRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.EmployeeResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateEmployeeRequest;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class EmployeeDesktopMapper {

  private static final DateTimeFormatter DISPLAY_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  private EmployeeDesktopMapper() {}

  public static CreateEmployeeCommand toCreateCommand(final CreateEmployeeRequest request) {
    return new CreateEmployeeCommand(
        request.id(),
        request.documentNumber(),
        request.firstName(),
        request.lastName(),
        request.email(),
        request.phone().isBlank() ? null : request.phone(),
        new BigDecimal(request.baseSalary()));
  }

  public static UpdateEmployeeCommand toUpdateCommand(final UpdateEmployeeRequest request) {
    return new UpdateEmployeeCommand(
        request.id(),
        request.documentNumber(),
        request.firstName(),
        request.lastName(),
        request.email(),
        request.phone().isBlank() ? null : request.phone(),
        new BigDecimal(request.baseSalary()),
        request.status());
  }

  public static FindEmployeeByIdQuery toFindByIdQuery(final String id) {
    return new FindEmployeeByIdQuery(id);
  }

  public static DeleteEmployeeCommand toDeleteCommand(final String id) {
    return new DeleteEmployeeCommand(id);
  }

  public static EmployeeResponse toResponse(final EmployeeModel employee) {
    return new EmployeeResponse(
        employee.getId().value(),
        employee.getDocumentNumber().value(),
        employee.getFirstName(),
        employee.getLastName(),
        employee.getEmail().value(),
        employee.getPhone() != null ? employee.getPhone() : "",
        employee.getBaseSalary().value().toPlainString(),
        employee.getStatus().name(),
        employee.getCreatedAt().format(DISPLAY_FORMAT),
        employee.getUpdatedAt().format(DISPLAY_FORMAT));
  }

  public static List<EmployeeResponse> toResponseList(final List<EmployeeModel> employees) {
    return employees.stream().map(EmployeeDesktopMapper::toResponse).toList();
  }
}
