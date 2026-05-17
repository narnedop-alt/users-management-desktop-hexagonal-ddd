package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ContractModel;

public interface UpdateContractUseCase {
  ContractModel execute(ContractModel contract);
}
