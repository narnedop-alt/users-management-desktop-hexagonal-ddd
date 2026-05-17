package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.FinishContractUseCase;
import com.jcaa.usersmanagement.application.port.out.GetContractByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateContractPort;
import com.jcaa.usersmanagement.application.service.dto.command.FinishContractCommand;
import com.jcaa.usersmanagement.application.service.mapper.ContractApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ContractNotFoundException;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FinishContractService implements FinishContractUseCase {

  private final UpdateContractPort updateContractPort;
  private final GetContractByIdPort getContractByIdPort;
  private final Validator validator;

  @Override
  public ContractModel execute(final FinishContractCommand command) {
    validateCommand(command);

    final ContractId contractId = ContractApplicationMapper.fromFinishCommandToContractId(command);
    final ContractModel current = findExistingContractOrFail(contractId);
    final ContractModel contractToFinish =
        ContractApplicationMapper.fromFinishCommandToModel(command, current);

    return updateContractPort.update(contractToFinish);
  }

  private void validateCommand(final FinishContractCommand command) {
    final Set<ConstraintViolation<FinishContractCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private ContractModel findExistingContractOrFail(final ContractId contractId) {
    return getContractByIdPort
        .getById(contractId)
        .orElseThrow(() -> ContractNotFoundException.becauseIdWasNotFound(contractId.value()));
  }
}
