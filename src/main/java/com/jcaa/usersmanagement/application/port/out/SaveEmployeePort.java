package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;

public interface SaveEmployeePort {
  EmployeeModel save(EmployeeModel employee);
}
