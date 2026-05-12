package com.jcaa.usersmanagement.domain.exception;

public final class InvalidDocumentNumberException extends DomainException {

  private static final String MESSAGE_EMPTY          = "The document number must not be empty.";
  private static final String MESSAGE_INVALID_FORMAT = "The document number format is invalid: '%s'. Only digits (6-20) are allowed.";

  private InvalidDocumentNumberException(final String message) {
    super(message);
  }

  public static InvalidDocumentNumberException becauseValueIsEmpty() {
    return new InvalidDocumentNumberException(MESSAGE_EMPTY);
  }

  public static InvalidDocumentNumberException becauseFormatIsInvalid(final String value) {
    return new InvalidDocumentNumberException(String.format(MESSAGE_INVALID_FORMAT, value));
  }
}
