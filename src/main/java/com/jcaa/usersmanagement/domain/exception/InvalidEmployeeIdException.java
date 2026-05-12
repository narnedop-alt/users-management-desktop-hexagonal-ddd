package com.jcaa.usersmanagement.domain.exception;

public final class InvalidEmployeeIdException extends DomainException {

  private static final String MESSAGE_EMPTY = "The employee id must not be empty.";

  private InvalidEmployeeIdException(final String message) {
    super(message);
  }

  public static InvalidEmployeeIdException becauseValueIsEmpty() {
    return new InvalidEmployeeIdException(MESSAGE_EMPTY);
  }
}
