package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import java.time.LocalDate;

public interface FinishContractUseCase {
  ContractModel execute(ContractId contractId, LocalDate endDate);
}
