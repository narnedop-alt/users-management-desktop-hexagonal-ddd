package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;

public interface FindEmployeeByIdUseCase {
  EmployeeModel execute(EmployeeId employeeId);
}
