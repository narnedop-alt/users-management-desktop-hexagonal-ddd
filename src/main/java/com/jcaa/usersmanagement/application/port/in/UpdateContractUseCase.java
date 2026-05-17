package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateContractCommand;
import com.jcaa.usersmanagement.domain.model.ContractModel;

public interface UpdateContractUseCase {
  ContractModel execute(UpdateContractCommand command);
}
