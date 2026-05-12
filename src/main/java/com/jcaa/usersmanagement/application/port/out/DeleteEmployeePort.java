package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;

public interface DeleteEmployeePort {
  void delete(EmployeeId employeeId);
}
