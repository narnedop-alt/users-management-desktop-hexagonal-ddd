package com.jcaa.usersmanagement.domain.exception;

public final class InvalidContractNumberException extends DomainException {

  private static final String MESSAGE_EMPTY          = "The contract number must not be empty.";
  private static final String MESSAGE_INVALID_LENGTH =
      "The contract number must have between 4 and 30 characters.";
  private static final String MESSAGE_INVALID_FORMAT =
      "The contract number format is invalid: '%s'. Only letters, numbers, hyphen and underscore are allowed.";

  private InvalidContractNumberException(final String message) {
    super(message);
  }

  public static InvalidContractNumberException becauseValueIsEmpty() {
    return new InvalidContractNumberException(MESSAGE_EMPTY);
  }

  public static InvalidContractNumberException becauseLengthIsInvalid() {
    return new InvalidContractNumberException(MESSAGE_INVALID_LENGTH);
  }

  public static InvalidContractNumberException becauseFormatIsInvalid(final String value) {
    return new InvalidContractNumberException(String.format(MESSAGE_INVALID_FORMAT, value));
  }
}
