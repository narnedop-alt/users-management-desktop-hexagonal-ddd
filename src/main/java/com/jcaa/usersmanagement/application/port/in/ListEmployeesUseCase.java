package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import java.util.List;

public interface ListEmployeesUseCase {
  List<EmployeeModel> execute();
}
