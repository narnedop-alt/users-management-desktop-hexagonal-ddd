package com.jcaa.usersmanagement.domain.exception;

public final class EmployeeNotFoundException extends DomainException {

  private static final String MESSAGE_BY_ID       = "The employee with id '%s' was not found.";
  private static final String MESSAGE_BY_DOCUMENT = "The employee with document number '%s' was not found.";

  private EmployeeNotFoundException(final String message) {
    super(message);
  }

  public static EmployeeNotFoundException becauseIdWasNotFound(final String employeeId) {
    return new EmployeeNotFoundException(String.format(MESSAGE_BY_ID, employeeId));
  }

  public static EmployeeNotFoundException becauseDocumentWasNotFound(final String documentNumber) {
    return new EmployeeNotFoundException(String.format(MESSAGE_BY_DOCUMENT, documentNumber));
  }
}
