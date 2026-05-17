package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import java.util.List;

public interface GetContractsByEmployeeIdPort {
  List<ContractModel> getByEmployeeId(EmployeeId employeeId);
}
