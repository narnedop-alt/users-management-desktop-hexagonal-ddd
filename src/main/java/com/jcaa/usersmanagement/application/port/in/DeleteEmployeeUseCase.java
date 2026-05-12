package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.DeleteEmployeeCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface DeleteEmployeeUseCase {
  void execute(@NotNull @Valid DeleteEmployeeCommand command);
}
