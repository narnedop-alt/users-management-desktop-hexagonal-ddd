package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateEmployeeCommand;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateEmployeeUseCase {
  EmployeeModel execute(@NotNull @Valid CreateEmployeeCommand command);
}
