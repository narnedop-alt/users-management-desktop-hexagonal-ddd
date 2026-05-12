package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;

public interface DeleteEmployeeUseCase {
  void execute(EmployeeId employeeId);
}
