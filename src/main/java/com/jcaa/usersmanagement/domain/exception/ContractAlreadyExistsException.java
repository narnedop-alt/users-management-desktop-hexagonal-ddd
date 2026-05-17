package com.jcaa.usersmanagement.domain.exception;

public final class ContractAlreadyExistsException extends DomainException {

  private static final String MESSAGE_NUMBER_EXISTS =
      "A contract with number '%s' already exists.";

  private ContractAlreadyExistsException(final String message) {
    super(message);
  }

  public static ContractAlreadyExistsException becauseNumberAlreadyExists(
      final String contractNumber) {
    return new ContractAlreadyExistsException(String.format(MESSAGE_NUMBER_EXISTS, contractNumber));
  }
}
