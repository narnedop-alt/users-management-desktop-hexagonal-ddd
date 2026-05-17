package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ContractModel;

public interface SaveContractPort {
  ContractModel save(ContractModel contract);
}
