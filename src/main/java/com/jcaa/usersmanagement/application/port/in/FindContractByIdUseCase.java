package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.FindContractByIdQuery;
import com.jcaa.usersmanagement.domain.model.ContractModel;

public interface FindContractByIdUseCase {
  ContractModel execute(FindContractByIdQuery query);
}
