package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteEmployeeUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteEmployeePort;
import com.jcaa.usersmanagement.application.port.out.GetEmployeeByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteEmployeeCommand;
import com.jcaa.usersmanagement.application.service.mapper.EmployeeApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteEmployeeService implements DeleteEmployeeUseCase {

  private final DeleteEmployeePort deleteEmployeePort;
  private final GetEmployeeByIdPort getEmployeeByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteEmployeeCommand command) {
    validateCommand(command);

    final EmployeeId employeeId = EmployeeApplicationMapper.fromDeleteCommandToEmployeeId(command);
    ensureEmployeeExists(employeeId);
    deleteEmployeePort.delete(employeeId);
  }

  private void validateCommand(final DeleteEmployeeCommand command) {
    final Set<ConstraintViolation<DeleteEmployeeCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureEmployeeExists(final EmployeeId employeeId) {
    getEmployeeByIdPort
        .getById(employeeId)
        .orElseThrow(() -> EmployeeNotFoundException.becauseIdWasNotFound(employeeId.value()));
  }
}
