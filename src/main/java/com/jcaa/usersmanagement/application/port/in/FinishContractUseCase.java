package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.FinishContractCommand;
import com.jcaa.usersmanagement.domain.model.ContractModel;

public interface FinishContractUseCase {
  ContractModel execute(FinishContractCommand command);
}
