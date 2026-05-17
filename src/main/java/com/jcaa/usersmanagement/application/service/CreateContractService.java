package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateContractUseCase;
import com.jcaa.usersmanagement.application.port.out.GetContractByNumberPort;
import com.jcaa.usersmanagement.application.port.out.GetEmployeeByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveContractPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateContractCommand;
import com.jcaa.usersmanagement.application.service.mapper.ContractApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.ContractAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.domain.model.ContractModel;
import com.jcaa.usersmanagement.domain.valueobject.ContractNumber;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateContractService implements CreateContractUseCase {

  private final SaveContractPort saveContractPort;
  private final GetContractByNumberPort getContractByNumberPort;
  private final GetEmployeeByIdPort getEmployeeByIdPort;
  private final Validator validator;

  @Override
  public ContractModel execute(final CreateContractCommand command) {
    validateCommand(command);

    final EmployeeId employeeId = new EmployeeId(command.employeeId());
    ensureEmployeeExists(employeeId);

    final ContractNumber contractNumber = new ContractNumber(command.contractNumber());
    ensureContractNumberIsNotTaken(contractNumber);

    final ContractModel contractToSave = ContractApplicationMapper.fromCreateCommandToModel(command);
    return saveContractPort.save(contractToSave);
  }

  private void validateCommand(final CreateContractCommand command) {
    final Set<ConstraintViolation<CreateContractCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureEmployeeExists(final EmployeeId employeeId) {
    getEmployeeByIdPort
        .getById(employeeId)
        .orElseThrow(() -> EmployeeNotFoundException.becauseIdWasNotFound(employeeId.value()));
  }

  private void ensureContractNumberIsNotTaken(final ContractNumber contractNumber) {
    getContractByNumberPort
        .getByContractNumber(contractNumber)
        .ifPresent(
            ignored -> {
              throw ContractAlreadyExistsException.becauseNumberAlreadyExists(
                  contractNumber.value());
            });
  }
}
