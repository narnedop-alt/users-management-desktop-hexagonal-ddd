package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import java.util.Optional;

public interface GetContractByIdPort {
  Optional<ContractModel> getById(ContractId contractId);
}
