package com.jcaa.usersmanagement.domain.exception;

public final class InvalidContractIdException extends DomainException {

  private static final String MESSAGE_EMPTY          = "The contract id must not be empty.";
  private static final String MESSAGE_INVALID_FORMAT = "The contract id format is invalid: '%s'.";

  private InvalidContractIdException(final String message) {
    super(message);
  }

  public static InvalidContractIdException becauseValueIsEmpty() {
    return new InvalidContractIdException(MESSAGE_EMPTY);
  }

  public static InvalidContractIdException becauseFormatIsInvalid(final String value) {
    return new InvalidContractIdException(String.format(MESSAGE_INVALID_FORMAT, value));
  }
}
