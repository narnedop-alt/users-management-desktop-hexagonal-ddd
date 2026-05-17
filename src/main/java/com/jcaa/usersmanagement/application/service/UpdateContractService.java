package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateContractUseCase;
import com.jcaa.usersmanagement.application.port.out.GetContractByIdPort;
import com.jcaa.usersmanagement.application.port.out.GetContractByNumberPort;
import com.jcaa.usersmanagement.application.port.out.GetEmployeeByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateContractPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateContractCommand;
import com.jcaa.usersmanagement.application.service.mapper.ContractApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ContractAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.ContractNotFoundException;
import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractId;
import com.jcaa.usersmanagement.domain.valueobject.ContractNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateContractService implements UpdateContractUseCase {

  private final UpdateContractPort updateContractPort;
  private final GetContractByIdPort getContractByIdPort;
  private final GetContractByNumberPort getContractByNumberPort;
  private final GetEmployeeByIdPort getEmployeeByIdPort;
  private final Validator validator;

  @Override
  public ContractModel execute(final UpdateContractCommand command) {
    validateCommand(command);

    final ContractId contractId = new ContractId(command.id());
    final ContractModel current = findExistingContractOrFail(contractId);

    final EmployeeId employeeId = new EmployeeId(command.employeeId());
    ensureEmployeeExists(employeeId);

    final ContractNumber contractNumber = new ContractNumber(command.contractNumber());
    ensureContractNumberIsNotTakenByAnother(contractNumber, contractId);

    final ContractModel contractToUpdate =
        ContractApplicationMapper.fromUpdateCommandToModel(command, current);
    return updateContractPort.update(contractToUpdate);
  }

  private void validateCommand(final UpdateContractCommand command) {
    final Set<ConstraintViolation<UpdateContractCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private ContractModel findExistingContractOrFail(final ContractId contractId) {
    return getContractByIdPort
        .getById(contractId)
        .orElseThrow(() -> ContractNotFoundException.becauseIdWasNotFound(contractId.value()));
  }

  private void ensureEmployeeExists(final EmployeeId employeeId) {
    getEmployeeByIdPort
        .getById(employeeId)
        .orElseThrow(() -> EmployeeNotFoundException.becauseIdWasNotFound(employeeId.value()));
  }

  private void ensureContractNumberIsNotTakenByAnother(
      final ContractNumber contractNumber, final ContractId ownerId) {
    getContractByNumberPort
        .getByContractNumber(contractNumber)
        .ifPresent(
            found -> {
              if (!found.getId().equals(ownerId)) {
                throw ContractAlreadyExistsException.becauseNumberAlreadyExists(
                    contractNumber.value());
              }
            });
  }
}
