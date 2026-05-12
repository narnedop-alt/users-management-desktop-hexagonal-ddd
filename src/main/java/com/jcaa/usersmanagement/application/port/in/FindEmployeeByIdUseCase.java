package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.FindEmployeeByIdQuery;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface FindEmployeeByIdUseCase {
  EmployeeModel execute(@NotNull @Valid FindEmployeeByIdQuery query);
}
