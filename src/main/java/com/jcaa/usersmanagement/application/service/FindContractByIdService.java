package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.FindContractByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetContractByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.FindContractByIdQuery;
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
public final class FindContractByIdService implements FindContractByIdUseCase {

  private final GetContractByIdPort getContractByIdPort;
  private final Validator validator;

  @Override
  public ContractModel execute(final FindContractByIdQuery query) {
    validateQuery(query);

    final ContractId contractId = ContractApplicationMapper.fromFindByIdQueryToContractId(query);
    return getContractByIdPort
        .getById(contractId)
        .orElseThrow(() -> ContractNotFoundException.becauseIdWasNotFound(contractId.value()));
  }

  private void validateQuery(final FindContractByIdQuery query) {
    final Set<ConstraintViolation<FindContractByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
