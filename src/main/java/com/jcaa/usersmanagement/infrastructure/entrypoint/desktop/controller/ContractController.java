package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateContractUseCase;
import com.jcaa.usersmanagement.application.port.in.FindContractByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.FinishContractUseCase;
import com.jcaa.usersmanagement.application.port.in.ListContractsUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateContractUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.ContractResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateContractRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateContractRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.ContractDesktopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ContractController {

  private final CreateContractUseCase createContractUseCase;
  private final UpdateContractUseCase updateContractUseCase;
  private final FinishContractUseCase finishContractUseCase;
  private final FindContractByIdUseCase findContractByIdUseCase;
  private final ListContractsUseCase listContractsUseCase;

  public List<ContractResponse> listAllContracts() {
    final var contracts = listContractsUseCase.execute();
    return ContractDesktopMapper.toResponseList(contracts);
  }

  public ContractResponse findContractById(final String id) {
    final var query = ContractDesktopMapper.toFindByIdQuery(id);
    final var contract = findContractByIdUseCase.execute(query);
    return ContractDesktopMapper.toResponse(contract);
  }

  public ContractResponse createContract(final CreateContractRequest request) {
    final var command = ContractDesktopMapper.toCreateCommand(request);
    final var contract = createContractUseCase.execute(command);
    return ContractDesktopMapper.toResponse(contract);
  }

  public ContractResponse updateContract(final UpdateContractRequest request) {
    final var command = ContractDesktopMapper.toUpdateCommand(request);
    final var contract = updateContractUseCase.execute(command);
    return ContractDesktopMapper.toResponse(contract);
  }

  public ContractResponse finishContract(final String id, final String endDate) {
    final var command = ContractDesktopMapper.toFinishCommand(id, endDate);
    final var contract = finishContractUseCase.execute(command);
    return ContractDesktopMapper.toResponse(contract);
  }
}
