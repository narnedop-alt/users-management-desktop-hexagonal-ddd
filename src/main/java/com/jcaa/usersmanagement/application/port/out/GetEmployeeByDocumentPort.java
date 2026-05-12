package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.DocumentNumber;
import java.util.Optional;

public interface GetEmployeeByDocumentPort {
  Optional<EmployeeModel> getByDocumentNumber(DocumentNumber documentNumber);
}
