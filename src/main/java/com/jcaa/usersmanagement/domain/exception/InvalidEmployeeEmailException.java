package com.jcaa.usersmanagement.domain.exception;

public final class InvalidEmployeeEmailException extends DomainException {

  private static final String MESSAGE_EMPTY          = "The employee email must not be empty.";
  private static final String MESSAGE_INVALID_FORMAT = "The employee email format is invalid: '%s'.";

  private InvalidEmployeeEmailException(final String message) {
    super(message);
  }

  public static InvalidEmployeeEmailException becauseValueIsEmpty() {
    return new InvalidEmployeeEmailException(MESSAGE_EMPTY);
  }

  public static InvalidEmployeeEmailException becauseFormatIsInvalid(final String email) {
    return new InvalidEmployeeEmailException(String.format(MESSAGE_INVALID_FORMAT, email));
  }
}
