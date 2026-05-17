package com.jcaa.usersmanagement.domain.exception;

public final class ContractNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID     = "The contract with id '%s' was not found.";
  private static final String MESSAGE_BY_NUMBER = "The contract with number '%s' was not found.";

  private ContractNotFoundException(final String message) {
    super(message);
  }

  public static ContractNotFoundException becauseIdWasNotFound(final String contractId) {
    return new ContractNotFoundException(String.format(MESSAGE_BY_ID, contractId));
  }

  public static ContractNotFoundException becauseNumberWasNotFound(final String contractNumber) {
    return new ContractNotFoundException(String.format(MESSAGE_BY_NUMBER, contractNumber));
  }
}
