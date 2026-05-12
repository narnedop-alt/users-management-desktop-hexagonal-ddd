package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;

public interface CreateEmployeeUseCase {
  EmployeeModel execute(EmployeeModel employee);
}
