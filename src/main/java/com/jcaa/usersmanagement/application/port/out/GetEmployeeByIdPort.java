package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import java.util.Optional;

public interface GetEmployeeByIdPort {
  Optional<EmployeeModel> getById(EmployeeId employeeId);
}
