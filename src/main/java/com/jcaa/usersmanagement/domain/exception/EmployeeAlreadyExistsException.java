package com.jcaa.usersmanagement.domain.exception;

public final class EmployeeAlreadyExistsException extends DomainException {

  private static final String MESSAGE_EMAIL_EXISTS    = "An employee with email '%s' already exists.";
  private static final String MESSAGE_DOCUMENT_EXISTS = "An employee with document number '%s' already exists.";

  private EmployeeAlreadyExistsException(final String message) {
    super(message);
  }

  public static EmployeeAlreadyExistsException becauseEmailAlreadyExists(final String email) {
    return new EmployeeAlreadyExistsException(String.format(MESSAGE_EMAIL_EXISTS, email));
  }

  public static EmployeeAlreadyExistsException becauseDocumentAlreadyExists(final String documentNumber) {
    return new EmployeeAlreadyExistsException(String.format(MESSAGE_DOCUMENT_EXISTS, documentNumber));
  }
}
