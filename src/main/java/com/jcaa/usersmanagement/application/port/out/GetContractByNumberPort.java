package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractNumber;
import java.util.Optional;

public interface GetContractByNumberPort {
  Optional<ContractModel> getByContractNumber(ContractNumber contractNumber);
}
