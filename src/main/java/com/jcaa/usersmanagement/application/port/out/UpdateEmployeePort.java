package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;

public interface UpdateEmployeePort {
  EmployeeModel update(EmployeeModel employee);
}
