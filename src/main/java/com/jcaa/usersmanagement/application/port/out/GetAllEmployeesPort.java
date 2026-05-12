package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import java.util.List;

public interface GetAllEmployeesPort {
  List<EmployeeModel> getAll();
}
