package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ContractModel;

public interface UpdateContractPort {
  ContractModel update(ContractModel contract);
}
