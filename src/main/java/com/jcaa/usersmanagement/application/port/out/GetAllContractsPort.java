package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.ContractModel;
import java.util.List;

public interface GetAllContractsPort {
  List<ContractModel> getAll();
}
