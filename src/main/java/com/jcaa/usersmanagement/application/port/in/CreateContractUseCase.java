package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateContractCommand;
import com.jcaa.usersmanagement.domain.model.ContractModel;

public interface CreateContractUseCase {
  ContractModel execute(CreateContractCommand command);
}
