package com.jcaa.usersmanagement.domain.exception;

public final class InvalidBaseSalaryException extends DomainException {

  private static final String MESSAGE_NULL          = "The base salary must not be null.";
  private static final String MESSAGE_NOT_POSITIVE  = "The base salary must be greater than zero.";
  private static final String MESSAGE_SCALE         = "The base salary must have at most 2 decimal places.";

  private InvalidBaseSalaryException(final String message) {
    super(message);
  }

  public static InvalidBaseSalaryException becauseValueIsNull() {
    return new InvalidBaseSalaryException(MESSAGE_NULL);
  }

  public static InvalidBaseSalaryException becauseValueIsNotPositive() {
    return new InvalidBaseSalaryException(MESSAGE_NOT_POSITIVE);
  }

  public static InvalidBaseSalaryException becauseScaleExceedsLimit() {
    return new InvalidBaseSalaryException(MESSAGE_SCALE);
  }
}
