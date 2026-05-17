package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;

public interface FindContractByIdUseCase {
  ContractModel execute(ContractId contractId);
}
