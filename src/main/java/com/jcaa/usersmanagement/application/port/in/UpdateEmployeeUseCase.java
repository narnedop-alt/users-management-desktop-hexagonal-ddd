package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;

public interface UpdateEmployeeUseCase {
  EmployeeModel execute(EmployeeModel employee);
}
