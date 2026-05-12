package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.FindEmployeeByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetEmployeeByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.FindEmployeeByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.EmployeeApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.EmployeeNotFoundException;
import com.jcaa.usersmanagement.domain.model.EmployeeModel;
import com.jcaa.usersmanagement.domain.valueobject.EmployeeId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindEmployeeByIdService implements FindEmployeeByIdUseCase {

  private final GetEmployeeByIdPort getEmployeeByIdPort;
  private final Validator validator;

  @Override
  public EmployeeModel execute(final FindEmployeeByIdQuery query) {
    validateQuery(query);

    final EmployeeId employeeId = EmployeeApplicationMapper.fromFindByIdQueryToEmployeeId(query);
    return getEmployeeByIdPort
        .getById(employeeId)
        .orElseThrow(() -> EmployeeNotFoundException.becauseIdWasNotFound(employeeId.value()));
  }

  private void validateQuery(final FindEmployeeByIdQuery query) {
    final Set<ConstraintViolation<FindEmployeeByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
