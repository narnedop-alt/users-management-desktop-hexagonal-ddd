package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.ContractModel;
import java.util.List;

public interface ListContractsUseCase {
  List<ContractModel> execute();
}
