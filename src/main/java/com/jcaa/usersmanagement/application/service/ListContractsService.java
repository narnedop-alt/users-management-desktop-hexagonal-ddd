package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.ListContractsUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllContractsPort;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListContractsService implements ListContractsUseCase {

  private final GetAllContractsPort getAllContractsPort;

  @Override
  public List<ContractModel> execute() {
    return getAllContractsPort.getAll();
  }
}
