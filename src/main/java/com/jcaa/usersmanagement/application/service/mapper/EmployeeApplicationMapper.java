package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEmployeeCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEmployeeCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateEmployeeCommand;
import com.jcaa.usersmanagement.application.service.dto.query.FindEmployeeByIdQuery;
import com.jcaa.usersmanagement.domain.enums.EmployeeStatus;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.BaseSalary;
import com.jcaa.usersmanagement.domain.valueobject.DocumentNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeEmail;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeApplicationMapper {

  public EmployeeModel fromCreateCommandToModel(final CreateEmployeeCommand command) {
    return EmployeeModel.create(
        new EmployeeId(command.id()),
        new DocumentNumber(command.documentNumber()),
        command.firstName(),
        command.lastName(),
        new EmployeeEmail(command.email()),
        command.phone(),
        new BaseSalary(command.baseSalary()));
  }

  public EmployeeModel fromUpdateCommandToModel(
      final UpdateEmployeeCommand command, final EmployeeModel current) {
    return new EmployeeModel(
        new EmployeeId(command.id()),
        new DocumentNumber(command.documentNumber()),
        command.firstName(),
        command.lastName(),
        new EmployeeEmail(command.email()),
        command.phone(),
        new BaseSalary(command.baseSalary()),
        EmployeeStatus.fromString(command.status()),
        current.getCreatedAt(),
        LocalDateTime.now());
  }

  public EmployeeId fromFindByIdQueryToEmployeeId(final FindEmployeeByIdQuery query) {
    return new EmployeeId(query.id());
  }

  public EmployeeId fromDeleteCommandToEmployeeId(final DeleteEmployeeCommand command) {
    return new EmployeeId(command.id());
  }
}
