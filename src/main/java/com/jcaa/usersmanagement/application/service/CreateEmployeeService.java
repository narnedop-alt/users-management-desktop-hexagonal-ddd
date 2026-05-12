package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateEmployeeUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEmployeeByDocumentPort;
import com.jcaa.usersmanagement.application.port.out.SaveEmployeePort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateEmployeeCommand;
import com.jcaa.usersmanagement.application.service.mapper.EmployeeApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EmployeeAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.DocumentNumber;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateEmployeeService implements CreateEmployeeUseCase {

  private final SaveEmployeePort saveEmployeePort;
  private final GetEmployeeByDocumentPort getEmployeeByDocumentPort;
  private final Validator validator;

  @Override
  public EmployeeModel execute(final CreateEmployeeCommand command) {
    validateCommand(command);

    final DocumentNumber documentNumber = new DocumentNumber(command.documentNumber());
    ensureDocumentIsNotTaken(documentNumber);

    final EmployeeModel employeeToSave = EmployeeApplicationMapper.fromCreateCommandToModel(command);
    return saveEmployeePort.save(employeeToSave);
  }

  private void validateCommand(final CreateEmployeeCommand command) {
    final Set<ConstraintViolation<CreateEmployeeCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureDocumentIsNotTaken(final DocumentNumber documentNumber) {
    getEmployeeByDocumentPort
        .getByDocumentNumber(documentNumber)
        .ifPresent(
            ignored -> {
              throw EmployeeAlreadyExistsException.becauseDocumentAlreadyExists(
                  documentNumber.value());
            });
  }
}
